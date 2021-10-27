package com.algaworks.algafood.api.dto;

import lombok.Builder;
import lombok.Getter;

import java.io.InputStream;

@Builder
@Getter
public class FotoRecuperadaDTO {

    private InputStream inputStream;
    private String url;

    public boolean temUrl() {
        return url != null;
    }

    public boolean temInputStream() {
        return inputStream != null;
    }

}
