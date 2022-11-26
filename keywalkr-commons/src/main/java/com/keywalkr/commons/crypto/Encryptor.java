package com.keywalkr.commons.crypto;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Encryptor {

    private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";
    private static final String ALGORITHM = "AES";
    private static final int IV_BYTES = 16;
    private static final byte[] IV_SPEC = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    public byte[] encrypt(byte[] message, byte[] keyByte) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        SecretKey secretKey = new SecretKeySpec(keyByte, ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(IV_SPEC));
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

    public Pair<byte[], String> encryptSecureSalted(byte[] message) {
        byte[] iv = new byte[IV_BYTES];
        new SecureRandom().nextBytes(iv);

        String salt = UUID.randomUUID().toString();
        String password = generateAlphanumericRandomPassword();

        try {
            SecretKeySpec secretKey = getSecretKeySpec(password, salt);

            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv));
            byte[] saltedEncrypted = cipher.doFinal(message);

            String arl = String.join(":", password, salt, Base64.getEncoder().encodeToString(iv));
            String spec = Base64.getEncoder().encodeToString(arl.getBytes());
            return new Pair<>(saltedEncrypted, spec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException |
                 InvalidAlgorithmParameterException | InvalidKeyException | IllegalBlockSizeException |
                 BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] decrypt(byte[] message, byte[] keyByte) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        SecretKey secretKey = new SecretKeySpec(keyByte, ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(IV_SPEC));
        return cipher.doFinal(message);
    }

    public byte[] decrypt(byte[] message, String arl) {
        String[] spec = arl.split(":");

        if (spec.length == 2) {
            return decrypt(message, spec[0], spec[1]);
        } else if (spec.length == 3) {
            return decryptSecureSalted(message, spec[0], spec[1], spec[2]);
        } else {
            throw new IllegalArgumentException();
        }
    }

    private byte[] decrypt(byte[] message, String secret, String salt) {

        try {
            SecretKey secretKey = new SecretKeySpec(salt.getBytes(), ALGORITHM);
            byte[] iv = Base64.getDecoder().decode(secret);

            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));
            return cipher.doFinal(message);
        } catch (InvalidAlgorithmParameterException | NoSuchPaddingException | InvalidKeyException |
                 BadPaddingException | NoSuchAlgorithmException | IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] decryptSecureSalted(byte[] message, String secret, String salt, String iv) {
        try {
            SecretKeySpec secretKey = getSecretKeySpec(secret, salt);

            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(Base64.getDecoder().decode(iv)));
            return cipher.doFinal(message);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException |
                 InvalidAlgorithmParameterException | InvalidKeyException | BadPaddingException |
                 IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        }
    }

    private SecretKeySpec getSecretKeySpec(String secret, String salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(secret.toCharArray(), salt.getBytes(), 65536, 256);
        SecretKey tmp = factory.generateSecret(spec);
        return new SecretKeySpec(tmp.getEncoded(), ALGORITHM);
    }

    private String generateRandomPassword(int len) {
        SecureRandom secureRandom = new SecureRandom();
        return secureRandom.ints(len, 90, 125)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    private String generateAlphanumericRandomPassword() {
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_-!()=@{}";
        SecureRandom random = new SecureRandom();
        return IntStream.range(0, 16)
                .map(i -> random.nextInt(chars.length()))
                .mapToObj(index -> String.valueOf(chars.charAt(index)))
                .collect(Collectors.joining());
    }
}
