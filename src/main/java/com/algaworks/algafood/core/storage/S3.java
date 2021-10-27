package com.algaworks.algafood.core.storage;

import com.amazonaws.regions.Regions;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class S3 {

    private String idChaveAcesso;
    private String chaveAcessoSecreta;
    private String bucket;
    private Regions regiao;
    private String diretorioFotos;

}
