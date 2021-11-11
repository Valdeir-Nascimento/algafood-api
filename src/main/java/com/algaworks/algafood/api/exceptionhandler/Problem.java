package com.algaworks.algafood.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.List;

//Incluir no json somente se for diferente de null
@ApiModel("Problema")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Builder
public class Problem {
    @ApiModelProperty(example = "400")
    private Integer status;
    private OffsetDateTime timestamp;

    @ApiModelProperty(example = "http://algafood.com.br/dados-invalidos")
    private String type;
    @ApiModelProperty(example = "Dados inválidos")
    private String title;

    private String detail;

    private String userMessage;

    private List<Field> fields;

    @ApiModel("ObjetoProblema")
    @Getter
    @Builder
    public static class Field {
        private String name;
        private String userMessage;
    }

}
