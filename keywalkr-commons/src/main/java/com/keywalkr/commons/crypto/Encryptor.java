package com.keywalkr.commons.crypto;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.UUID;

public class Encryptor {

    private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";
    private static final String ALGORITHM = "AES";
    private static final int IV_BYTES = 16;

    public byte[] encrypt(byte[] message, byte[] keyByte) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        SecretKey secretKey = new SecretKeySpec(keyByte, ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return cipher.doFinal(message);
    }

    public Pair<byte[], String> encrypt(byte[] bytesToEncrypt)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        String salt = UUID.randomUUID().toString().substring(0, 16);

        //create key
        byte[] iv = new byte[IV_BYTES];
        new SecureRandom().nextBytes(iv);
        SecretKey secretKey = new SecretKeySpec(salt.getBytes(), ALGORITHM);

        // encrypt
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv));
        byte[] encrypted = cipher.doFinal(bytesToEncrypt);

        String arl = Base64.getEncoder().encodeToString(iv).concat(":").concat(salt);

        return new Pair<>(encrypted, arl);
    }

    public byte[] decrypt(byte[] message, byte[] keyByte) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        SecretKey secretKey = new SecretKeySpec(keyByte, ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return cipher.doFinal(message);
    }

    public byte[] decrypt(byte[] message, String arl) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        String[] split = arl.split(":");
        return decrypt(message, split[0], split[1]);
    }

    private byte[] decrypt(byte[] message, String secret, String salt) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

        SecretKey secretKey = new SecretKeySpec(salt.getBytes(), ALGORITHM);
        byte[] iv = Base64.getDecoder().decode(secret);

        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));
        return cipher.doFinal(message);
    }
}
