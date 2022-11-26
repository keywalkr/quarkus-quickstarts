package com.keywalkr.quarkus.storage.mino.adapter.api.input.model;

import org.jboss.resteasy.reactive.PartType;
import org.jboss.resteasy.reactive.RestForm;

import javax.ws.rs.core.MediaType;
import java.io.File;

public class FormData {

    @RestForm("filename")
    @PartType(MediaType.TEXT_PLAIN)
    public String filename;

    @RestForm("file")
    public File file;
}
