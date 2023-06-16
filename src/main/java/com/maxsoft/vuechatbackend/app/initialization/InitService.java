package com.maxsoft.vuechatbackend.app.initialization;

import com.maxsoft.vuechatbackend.entity.Account;
import com.maxsoft.vuechatbackend.entity.PublicKey;
import com.maxsoft.vuechatbackend.entity.Utility;
import com.maxsoft.vuechatbackend.repository.AccountRepository;
import com.maxsoft.vuechatbackend.repository.PublicKeyRepository;
import com.maxsoft.vuechatbackend.repository.UtilRepository;
import com.maxsoft.vuechatbackend.util.RsaKeyConverterUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InitService {

    private final UtilRepository utilRepository;
    private final AccountRepository accountRepository;
    private final PublicKeyRepository publicKeyRepository;

    @Value("${app.init-props.key-rsa-size}")
    private int keySize;

    private String serverUsername;

    private String serverName;

    @Transactional
    public void recreateServerUser() throws NoSuchAlgorithmException {
        utilRepository.findById(Utility.Key.SERVER_USER_ID.name())
                .ifPresent(s -> accountRepository.deleteById(UUID.fromString(s.getUtilValue())));

        UUID id = UUID.randomUUID();

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(keySize);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        String publicKeyPem = RsaKeyConverterUtil.publicKeyToPem(keyPair.getPublic());
        String privateKeyPem = RsaKeyConverterUtil.privateKeyToPem(keyPair.getPrivate());


        PublicKey publicKey = new PublicKey();

        Account serverAccount = Account.builder()
                .id(id)
                .name("VueChat Administration")
                .username("administrator")
                .publicKeyList(List.of(publicKey))
                .build();

        publicKey.setValue(publicKeyPem);
        publicKey.setAccount(serverAccount);
        publicKey.setNumber(1);

        accountRepository.save(serverAccount);

        utilRepository.save(new Utility(Utility.Key.SERVER_USER_ID.name(), id.toString()));
        utilRepository.save(new Utility(Utility.Key.SERVER_USER_SECRET_KEY.name(), privateKeyPem));
    }

}
