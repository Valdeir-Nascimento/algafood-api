package com.algaworks.algafood.core.storage;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class S3 {

    private String idChaveAcesso;
    private String chaveAcessoSecreta;
    private String bucket;
    private String regiao;
    private String diretorioFotos;

}
