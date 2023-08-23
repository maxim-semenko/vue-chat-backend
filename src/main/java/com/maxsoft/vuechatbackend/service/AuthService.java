package com.maxsoft.vuechatbackend.service;

import com.maxsoft.vuechatbackend.controller.dto.AccountDto;
import com.maxsoft.vuechatbackend.controller.dto.AuthRequestDto;
import com.maxsoft.vuechatbackend.controller.dto.mapper.AccountMapper;
import com.maxsoft.vuechatbackend.controller.dto.mapper.EncryptAccountKeyMapper;
import com.maxsoft.vuechatbackend.entity.Account;
import com.maxsoft.vuechatbackend.entity.EncryptAccountKey;
import com.maxsoft.vuechatbackend.entity.enums.EncryptKeyType;
import com.maxsoft.vuechatbackend.repository.AccountRepository;
import com.maxsoft.vuechatbackend.repository.EncryptAccountKeyRepository;
import com.maxsoft.vuechatbackend.util.CryptUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final AccountRepository accountRepository;
    private final EncryptAccountKeyRepository encryptAccountKeyRepository;
    private final UtilService utilService;
    private final PasswordEncoder passwordEncoder;

    public AccountDto login(AuthRequestDto authRequestDto) {
        Account account = accountRepository
                .findByUsername(authRequestDto.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!passwordEncoder.matches(authRequestDto.getPassword(), account.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials provided");
        }

        String serverPrivateKey = utilService.getServerPrivateKey();
        EncryptAccountKey accountEncryptedAesKey = getEncryptAccountKey(account.getId(), EncryptKeyType.AES);
        EncryptAccountKey accountEncryptedPrivateKey = getEncryptAccountKey(account.getId(), EncryptKeyType.RSA);

        String decryptedAes = CryptUtil.decryptByRSA(
                accountEncryptedAesKey.getValue(),
                account.getPublicKey(),
                serverPrivateKey,
                accountEncryptedAesKey.getSignature(),
                accountEncryptedAesKey.getDigest()
        );

        String decodedAccountPrivateKey = CryptUtil.decryptByAES(
                accountEncryptedPrivateKey.getValue(),
                decryptedAes,
                accountEncryptedPrivateKey.getDigest()
        );

        EncryptAccountKey encryptedAesFromClient = EncryptAccountKeyMapper.prepareAesForDatabase(authRequestDto.getKeyAes(), account.getId());

        String decryptedAesFromClient = CryptUtil.decryptByRSA(
                encryptedAesFromClient.getValue(),
                authRequestDto.getPublicKey(),
                serverPrivateKey,
                encryptedAesFromClient.getSignature(),
                encryptedAesFromClient.getDigest()
        );

        CryptUtil.CryptoResultAES resultEncryptedPrivateKey = CryptUtil.encryptByAES(decodedAccountPrivateKey, decryptedAesFromClient);

        return prepareAccountDto(account, resultEncryptedPrivateKey);
    }

    @Transactional
    public AccountDto register(AuthRequestDto authRequestDto) {
        checkUsernameExists(authRequestDto.getUsername());

        Account account = Account.builder()
                .username(authRequestDto.getUsername())
                .firstname(authRequestDto.getFirstname())
                .lastname(authRequestDto.getLastname())
                .publicKey(authRequestDto.getPublicKey())
                .password(passwordEncoder.encode(authRequestDto.getPassword()))
                .build();

        account = accountRepository.save(account);

        EncryptAccountKey keyRsa = EncryptAccountKeyMapper.prepareRsaForDatabase(authRequestDto.getKeyRsa(), account.getId());
        EncryptAccountKey keyAes = EncryptAccountKeyMapper.prepareAesForDatabase(authRequestDto.getKeyAes(), account.getId());
        encryptAccountKeyRepository.saveAll(List.of(keyRsa, keyAes));

        return AccountMapper.convertToDto(account);
    }

    private void checkUsernameExists(String username) {
        Optional<Account> accountOptional = accountRepository.findByUsername(username);
        if (accountOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account already exists with username: " + username);
        }
    }

    private EncryptAccountKey getEncryptAccountKey(UUID accountId, EncryptKeyType type) {
        return encryptAccountKeyRepository
                .findByAccountIdAndType(accountId, type)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    private AccountDto prepareAccountDto(Account account, CryptUtil.CryptoResultAES privateKey) {
        AccountDto accountDto = AccountMapper.convertToDto(account);
        accountDto.setEncodedPrivateKey(EncryptAccountKeyMapper.prepareRsaForJson(privateKey.getData(), privateKey.getDigest()));

        return accountDto;
    }

}
