package com.keywalkr.quarkus.storage.mino.domain.model;

import lombok.Builder;
import lombok.Getter;

import java.io.File;

@Getter
@Builder(toBuilder = true)
public class UploadContent {

    private File file;
    private String filename;
    private String mimeType;
}
