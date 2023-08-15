package com.maxsoft.vuechatbackend.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;

public class CryptUtil {

    @Getter
    @AllArgsConstructor
    public static class CryptoResultRSA {
        private byte[] data;
        private byte[] signature;
        private byte[] digest;
    }

    @Getter
    @AllArgsConstructor
    public static class CryptoResultAES {
        private byte[] data;
        private byte[] digest;
    }

    public static CryptoResultRSA encryptByRSA(String input, String publicKeyPem, String privateKeyPem) throws InvalidKeyException, NoSuchAlgorithmException {
        PrivateKey privateKey = RsaKeyConverterUtil.pemToPrivateKey(privateKeyPem);
        PublicKey publicKey = RsaKeyConverterUtil.pemToPublicKey(publicKeyPem);

        Cipher cipher = CryptHelperUtil.getCipherRSAForEncrypt(publicKey);
        Signature signature = Signature.getInstance("SHA1withRSA");

        try {
            byte[] encryptedData = cipher.doFinal(input.getBytes());
            byte[] digest = Double.toString(Math.random() + 1).substring(2).getBytes();

            signature.initSign(privateKey);
            signature.update(digest);
            byte[] signatureBytes = signature.sign();

            return new CryptoResultRSA(encryptedData, signatureBytes, digest);
        } catch (IllegalBlockSizeException | BadPaddingException | SignatureException e) {
            throw new RuntimeException(e);
        }
    }

    public static String decryptByRSA(byte[] input, String publicKeyPem, String privateKeyPem, byte[] signature, byte[] nonce) throws IllegalArgumentException {
        PublicKey publicKeyToVerify = RsaKeyConverterUtil.pemToPublicKey(publicKeyPem);
        PrivateKey privateKeyToDecrypt = RsaKeyConverterUtil.pemToPrivateKey(privateKeyPem);

        boolean verify = CryptHelperUtil.verify(signature, nonce, publicKeyToVerify);
        if (!verify) {
            throw new IllegalArgumentException();
        }

        Cipher cipher = CryptHelperUtil.getCipherRSAForDecrypt(privateKeyToDecrypt);
        try {
            return new String(cipher.doFinal(input), StandardCharsets.UTF_8);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }
}
