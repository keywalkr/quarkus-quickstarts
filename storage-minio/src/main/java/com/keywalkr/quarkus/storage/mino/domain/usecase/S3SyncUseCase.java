package com.keywalkr.quarkus.storage.mino.domain.usecase;

import com.keywalkr.quarkus.storage.mino.adapter.api.input.model.FormData;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.IOException;

public interface S3SyncUseCase {

    PutObjectResponse uploadToS3(FormData data) throws IOException;

    PutObjectResponse uploadToS3Anonymous(FormData data) throws IOException;
}
