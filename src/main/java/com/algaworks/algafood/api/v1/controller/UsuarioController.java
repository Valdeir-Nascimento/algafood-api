package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.assembler.UsuarioDTOAssembler;
import com.algaworks.algafood.api.v1.assembler.UsuarioDTODisassembler;
import com.algaworks.algafood.api.v1.controller.swagger.UsuarioControllerSwagger;
import com.algaworks.algafood.api.v1.dto.UsuarioDTO;
import com.algaworks.algafood.api.v1.dto.input.SenhaInput;
import com.algaworks.algafood.api.v1.dto.input.UsuarioComSenhaInput;
import com.algaworks.algafood.api.v1.dto.input.UsuarioInput;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.service.CadastroUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/v1/usuarios", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioController implements UsuarioControllerSwagger {

    @Autowired
    private CadastroUsuarioService usuarioService;
    @Autowired
    private UsuarioDTOAssembler usuarioDTOAssembler;
    @Autowired
    private UsuarioDTODisassembler usuarioDTODisassembler;

    @GetMapping
    public CollectionModel<UsuarioDTO> listar() {
        return usuarioDTOAssembler.toCollectionModel(usuarioService.listar());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioDTO adicionar(@RequestBody @Valid UsuarioComSenhaInput usuarioInput) {
        Usuario usuario = usuarioDTODisassembler.toDomainObject(usuarioInput);
        usuario = usuarioService.salvar(usuario);
        return usuarioDTOAssembler.toModel(usuario);
    }

    @GetMapping("/{usuarioId}")
    public UsuarioDTO buscar(@PathVariable Long usuarioId) {
        Usuario usuario = usuarioService.buscarOuFalhar(usuarioId);
        return usuarioDTOAssembler.toModel(usuario);
    }

    @PutMapping("/{usuarioId}")
    public UsuarioDTO atualizar(
            @PathVariable Long usuarioId, @RequestBody @Valid UsuarioInput usuarioInput) {
        Usuario usuarioAtual = usuarioService.buscarOuFalhar(usuarioId);
        usuarioDTODisassembler.copyToDomainObject(usuarioInput, usuarioAtual);
        usuarioAtual = usuarioService.salvar(usuarioAtual);
        return usuarioDTOAssembler.toModel(usuarioAtual);
    }

    @PutMapping("/{usuarioId}/senha")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void alterarSenha(@PathVariable Long usuarioId, @RequestBody @Valid SenhaInput senhaInput) {
        usuarioService.alterarSenha(usuarioId, senhaInput.getSenhaAtual(), senhaInput.getNovaSenha());
    }

    @DeleteMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long usuarioId) {
        usuarioService.excluir(usuarioId);
    }

}
