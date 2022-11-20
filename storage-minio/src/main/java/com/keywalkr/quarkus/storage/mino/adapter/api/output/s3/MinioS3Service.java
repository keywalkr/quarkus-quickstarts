package com.keywalkr.quarkus.storage.mino.adapter.api.output.s3;

import com.keywalkr.commons.logger.KWLogger;
import com.keywalkr.quarkus.storage.mino.adapter.api.output.s3.model.UploadContent;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Map;
import java.util.UUID;

@ApplicationScoped
public class MinioS3Service {

    @Inject
    KWLogger log;

    @ConfigProperty(name = "s3.bucket.name")
    private String bucketName;

    @Inject
    private S3Client s3;

    public PutObjectResponse toS3(UploadContent content) {
        return s3.putObject(buildPutRequest(content), RequestBody.fromFile(content.getFile()));
    }

    public PutObjectResponse toS32(UploadContent content) {
        return s3.putObject(buildPutRequest2(content), RequestBody.fromFile(content.getFile()));
    }

    public ResponseBytes<GetObjectResponse> fromS3(String filePath) {
        return s3.getObjectAsBytes(buildGetRequest(filePath));
    }

    public DeleteObjectResponse deleteFromS3(String filePath) {
        return s3.deleteObject(DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(filePath)
                .build());
    }

    private PutObjectRequest buildPutRequest(UploadContent content) {
        return PutObjectRequest.builder()
                .bucket(bucketName)
                .key(content.getFilename())
                .metadata(Map.of("arl-sec", UUID.randomUUID().toString()))
                .contentType(content.getMimeType())
                .build();
    }

    private PutObjectRequest buildPutRequest2(UploadContent content) {
        String fileId = UUID.randomUUID().toString();
        String filePath = buildFileKey("EJ00AKR007", fileId);

        return PutObjectRequest.builder()
                .bucket(bucketName)
                .key(filePath)
                .metadata(Map.of("arl-sec", UUID.randomUUID().toString()))
                .contentType(content.getMimeType())
                .build();
    }

    private GetObjectRequest buildGetRequest(String filePath) {
        return GetObjectRequest.builder()
                .bucket(bucketName)
                .key(filePath)
                .build();
    }

    private String buildFileKey(String user, String id) {
        return String.format("%s/%s", user, id);
    }
}
