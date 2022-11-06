package com.keywalkr.quarkus.storage.mino.adapter.api.input;

import com.keywalkr.quarkus.storage.mino.adapter.api.input.model.FormData;
import com.keywalkr.quarkus.storage.mino.domain.model.UploadContent;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Map;
import java.util.UUID;

@ApplicationScoped
public class MinioS3Service {

    @ConfigProperty(name = "s3.bucket.name")
    private String bucketName;

    @Inject
    private S3Client s3;

    public PutObjectResponse toS3(FormData formData) {
        return s3.putObject(buildPutRequest(formData), RequestBody.fromFile(formData.file));
    }

    public PutObjectResponse toS3(UploadContent content) {
        return s3.putObject(buildPutRequest(content), RequestBody.fromFile(content.getFile()));
    }

    public ResponseBytes<GetObjectResponse> fromS3(String filePath) {
        return s3.getObjectAsBytes(buildGetRequest(filePath));
    }

    private PutObjectRequest buildPutRequest(FormData content) {

        return PutObjectRequest.builder()
                .bucket(bucketName)
                .key(content.filename)
                .metadata(Map.of("sep", UUID.randomUUID().toString()))
                .contentType("image/png")
                .build();
    }

    private PutObjectRequest buildPutRequest(UploadContent content) {

        return PutObjectRequest.builder()
                .bucket(bucketName)
                .key(content.getFilename())
                .metadata(Map.of("arl-sec", UUID.randomUUID().toString()))
                .contentType(content.getMimeType())
                .build();
    }

    private PutObjectRequest buildPutRequestAnnonym(UploadContent content) {
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
