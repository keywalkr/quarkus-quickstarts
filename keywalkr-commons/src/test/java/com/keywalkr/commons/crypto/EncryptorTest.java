package com.keywalkr.commons.crypto;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EncryptorTest {

    static final String KEY_STRING = "1234567890123456";

    Encryptor encryptor;

    @BeforeAll
    public void setUp() {
        encryptor = new Encryptor();
    }

    @Test
    void whenEncrypted_thenShouldNotEqualOriginal() throws Exception {
        String message = "This is a message";

        byte[] encryptedByte = encryptor.encrypt(message.getBytes(), KEY_STRING.getBytes());

        assertAll(
                () -> assertThat(encryptedByte).isNotNull(),
                () -> assertThat(encryptedByte.length % 32).isZero(),
                () -> assertThat(new String(encryptedByte)).isNotEqualTo(message)
        );
    }

    @Test
    void whenEncrypted_thenShouldNotEqualOriginalAndReturnPair() throws Exception {
        String message = "This is a message";

        Pair<byte[], String> encrypted = encryptor.encrypt(message.getBytes());

        assertAll(
                () -> assertThat(encrypted).isNotNull(),
                () -> assertThat(encrypted.getLeft()).isNotNull(),
                () -> assertThat(encrypted.getRight()).isNotNull(),
                () -> assertThat(encrypted.getLeft().length % 32).isZero(),
                () -> assertThat(new String(encrypted.getLeft())).isNotEqualTo(message)
        );
    }

    @Test
    void whenEncryptAndDecrypted_thenShouldEqualOriginal() throws Exception {
        String message = "This is a message to crypt";
        byte[] key = KEY_STRING.getBytes();

        byte[] encryptedByte = encryptor.encrypt(message.getBytes(), key);
        byte[] decryptedByte = encryptor.decrypt(encryptedByte, key);

        assertAll(
                () -> assertThat(decryptedByte).isNotNull(),
                () -> assertThat(new String(decryptedByte)).isNotEqualTo(new String(encryptedByte)),
                () -> assertThat(new String(decryptedByte)).isEqualTo(message)
        );
    }

    @Test
    void whenEncryptAndDecrypted_thenReturnPairAndShouldEqualOriginal() throws Exception {
        String message = "This is a message to crypt";

        Pair<byte[], String> encrypted = encryptor.encrypt(message.getBytes());
        byte[] decrypted = encryptor.decrypt(encrypted.getLeft(), encrypted.getRight());

        assertAll(
                () -> assertThat(decrypted).isNotNull(),
                () -> assertThat(new String(decrypted)).isNotEqualTo(new String(encrypted.getLeft())),
                () -> assertThat(new String(decrypted)).isEqualTo(message)
        );
    }

    @Test
    void whenSaltedEncryptedAndDecrypted_thenSuccess() {
        // Arrange
        String message = "I have a complicated word ???????? to ???$ and ?????? @kubertis";

        // Act
        Pair<byte[], String> encrypted = encryptor.encryptSecureSalted(message.getBytes());

        byte[] decryptedBytes = encryptor.decrypt(encrypted.getLeft(), new String(Base64.getDecoder().decode(encrypted.getRight())));

        // Assert
        assertAll(
                () -> assertThat(decryptedBytes).isNotNull(),
                () -> assertThat(new String(decryptedBytes)).isNotEqualTo(new String(encrypted.getLeft())),
                () -> assertThat(new String(decryptedBytes)).isEqualTo(message)
        );
    }
}