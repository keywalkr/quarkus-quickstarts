package com.keywalkr.quarkus.storage.mino.adapter.api.output.s3.model;

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
