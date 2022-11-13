package com.keywalkr.quarkus.storage.mino.domain.usecase.impl;

import com.keywalkr.quarkus.storage.mino.adapter.api.input.MinioS3Service;
import com.keywalkr.quarkus.storage.mino.adapter.api.input.model.FormData;
import com.keywalkr.quarkus.storage.mino.domain.model.UploadContent;
import com.keywalkr.quarkus.storage.mino.domain.usecase.S3SyncUseCase;
import org.apache.tika.Tika;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;

@ApplicationScoped
public class S3SyncUserCaseImpl implements S3SyncUseCase {

    @Inject
    MinioS3Service minioS3Service;

    @Override
    public PutObjectResponse uploadToS3(FormData data) throws IOException {

        String mimeType = new Tika().detect(data.file);

        UploadContent uploadContent = UploadContent.builder()
                .file(data.file)
                .filename(data.filename)
                .mimeType(mimeType)
                .build();

        return minioS3Service.toS3(uploadContent);
    }

    @Override
    public PutObjectResponse uploadToS3Anonymous(FormData data) throws IOException {
        String mimeType = new Tika().detect(data.file);

        UploadContent uploadContent = UploadContent.builder()
                .file(data.file)
                .filename(data.filename)
                .mimeType(mimeType)
                .build();

        return minioS3Service.toS32(uploadContent);
    }

}
