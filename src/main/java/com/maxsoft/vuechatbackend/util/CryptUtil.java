package com.maxsoft.vuechatbackend.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;

public class CryptUtil {

    private static final SecureRandom secureRandom = new SecureRandom();

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
//        if (!verify) {
//            throw new IllegalArgumentException();
//        }

        Cipher cipher = CryptHelperUtil.getCipherRSAForDecrypt(privateKeyToDecrypt);
        try {
            return new String(cipher.doFinal(input), StandardCharsets.ISO_8859_1);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            System.out.println(e.getMessage());
            throw new IllegalArgumentException(e);
        }
    }

    public static CryptoResultAES encryptByAES(String input, String key) {
        byte[] keyBytes = key.getBytes(StandardCharsets.ISO_8859_1);
        byte[] ivBytes = secureRandom.generateSeed(16);

        try {
            Cipher encryptCipher = CryptHelperUtil.getCipherAESForEncrypt(keyBytes, ivBytes);
            byte[] encryptedBytes = encryptCipher.doFinal(input.getBytes(StandardCharsets.UTF_8));

            return new CryptoResultAES(encryptedBytes, ivBytes);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            System.out.println(e.getMessage());
            throw new IllegalArgumentException(e);
        }
    }

    public static String decryptByAES(byte[] input, String key, byte[] digest) {
        byte[] keyBytes = key.getBytes(StandardCharsets.ISO_8859_1);
        Cipher decryptCipher = CryptHelperUtil.getCipherAESForDecrypt(keyBytes, digest);

        try {
            byte[] decryptedBytes = decryptCipher.doFinal(input);
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }

}
