package com.keywalkr.commons.crypto;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FileCryptTest {

    static final String KEY_STRING = "1234567890123456";

    FileCrypt fileCrypt;

    @BeforeAll
    void init(){
        fileCrypt = new FileCrypt();
    }

    @Test
    void name() {
        String name = "name";
        assertThat(name).isNotNull();
        assertThat(KEY_STRING).isNotNull();
    }
}