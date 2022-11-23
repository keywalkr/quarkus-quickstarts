package com.keywalkr.commons.crypto;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class FileCrypt {

    Encryptor encryptor;

    public byte[] encrypt(File file, byte[] keyBytes) throws IOException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        return encrypt(new FileInputStream(file), keyBytes);
    }

    public byte[] encrypt(InputStream inputStream, byte[] keyBytes) throws IOException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        encryptor = new Encryptor();
        byte[] bytes = inputStream.readAllBytes();
        return encryptor.encrypt(bytes, keyBytes);
    }
}
