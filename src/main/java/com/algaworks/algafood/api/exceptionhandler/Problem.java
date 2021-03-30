package com.algaworks.algafood.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.List;

//Incluir no json somente se for diferente de null
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Builder
public class Problem {
    private Integer status;
    private String type;
    private String title;
    private String detail;

    private String userMessage;
    private OffsetDateTime timestamp;
    private List<Field> fields;

    @Getter
    @Builder
    public static class Field {
        private String name;
        private String userMessage;
    }

}
