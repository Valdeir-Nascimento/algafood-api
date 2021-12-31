package com.algaworks.algafood.api.v1.controller.swagger;

import com.algaworks.algafood.api.v1.dto.UsuarioDTO;
import com.algaworks.algafood.api.v1.dto.input.SenhaInput;
import com.algaworks.algafood.api.v1.dto.input.UsuarioComSenhaInput;
import com.algaworks.algafood.api.v1.dto.input.UsuarioInput;
import com.algaworks.algafood.api.exceptionhandler.Problem;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;

@Api(tags = "Usuários")
public interface UsuarioControllerSwagger {

    @ApiOperation("Lista os usuários")
    CollectionModel<UsuarioDTO> listar() ;

    @ApiOperation("Busca um usuário por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID do usuário inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)
    })
    UsuarioDTO buscar(@ApiParam(value = "ID do usuário", example = "1", required = true) Long usuarioId);


    @ApiOperation("Cadastra um usuário")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Usuário cadastrado"),
    })
    UsuarioDTO adicionar(  @ApiParam(name = "corpo", value = "Representação de um novo usuário", required = true) UsuarioComSenhaInput usuarioInput);


    @ApiOperation("Atualiza um usuário por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Usuário atualizado"),
            @ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)
    })
    UsuarioDTO atualizar(
            @ApiParam(value = "ID do usuário", example = "1", required = true) Long usuarioId,
            @ApiParam(name = "corpo", value = "Representação de um usuário com os novos dados", required = true) UsuarioInput usuarioInput) ;


    @ApiOperation("Atualiza a senha de um usuário")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Senha alterada com sucesso"),
            @ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)
    })
    void alterarSenha(
            @ApiParam(value = "ID do usuário", example = "1", required = true) Long usuarioId,
            @ApiParam(name = "corpo", value = "Representação de uma nova senha",
                    required = true) SenhaInput senhaInput) ;


    void excluir(Long usuarioId) ;

}
