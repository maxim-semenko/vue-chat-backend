package com.maxsoft.vuechatbackend.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
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
        if (!verify) {
            throw new IllegalArgumentException();
        }

        Cipher cipher = CryptHelperUtil.getCipherRSAForDecrypt(privateKeyToDecrypt);
        try {
            return new String(cipher.doFinal(input), StandardCharsets.ISO_8859_1);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    public static CryptoResultAES encryptByAES(String input, String key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        byte[] keyBytes = key.getBytes();
        byte[] ivBytes = secureRandom.generateSeed(16);

        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(ivBytes);

        Cipher encryptCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        encryptCipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);

        byte[] encryptedBytes = encryptCipher.doFinal(input.getBytes(StandardCharsets.UTF_8));

        return new CryptoResultAES(encryptedBytes, ivBytes);
    }

    public static String decryptByAES(byte[] input, String key, byte[] digest) throws IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException {
        Cipher decryptCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

        byte[] keyBytes = key.getBytes(StandardCharsets.ISO_8859_1);
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(digest);

        decryptCipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
        byte[] decryptedBytes = decryptCipher.doFinal(input);

        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

}
