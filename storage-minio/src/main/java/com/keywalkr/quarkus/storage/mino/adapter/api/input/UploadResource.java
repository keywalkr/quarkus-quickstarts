package com.keywalkr.quarkus.storage.mino.adapter.api.input;

import com.keywalkr.quarkus.storage.mino.adapter.api.input.model.FormData;
import com.keywalkr.quarkus.storage.mino.domain.usecase.S3SyncUseCase;
import org.jboss.resteasy.reactive.MultipartForm;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("/s3")
@Produces(MediaType.APPLICATION_OCTET_STREAM)
public class UploadResource {

    @Inject
    S3SyncUseCase s3SyncUseCase;

    @Inject
    MinioS3Service minioS3Service;

    @POST
    @Path("upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response upload(@MultipartForm FormData formData) throws IOException {
        return Response.ok(s3SyncUseCase.uploadToS3(formData)).build();
    }

    @POST
    @Path("upload/ano")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadAnonymous(@MultipartForm FormData formData) throws IOException {
        return Response.ok(s3SyncUseCase.uploadToS3Anonymous(formData)).build();
    }

    @GET
    @Path("/download/{objectKey}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response downloadFile(String objectKey) {

        return buildResponse(minioS3Service.fromS3(objectKey));
        /*ResponseBytes<GetObjectResponse> objectBytes = minioS3Service.fromS3(objectKey);

        return Response.ok(objectBytes.asInputStream())
                .header("Content-Disposition", "attachment;filename=" + objectKey)
                .header("Content-Type", objectBytes.response().contentType())
                .build();*/
    }

    private Response buildResponse(ResponseBytes<GetObjectResponse> responseBytes) {
        return Response.ok(responseBytes.asInputStream())
                .header("Content-Type", responseBytes.response().contentType())
                .build();
    }
}
