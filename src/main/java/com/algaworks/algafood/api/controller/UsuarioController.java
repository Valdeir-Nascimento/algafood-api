package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.UsuarioDTOAssembler;
import com.algaworks.algafood.api.assembler.UsuarioDTODisassembler;
import com.algaworks.algafood.api.dto.UsuarioDTO;
import com.algaworks.algafood.api.dto.input.SenhaInput;
import com.algaworks.algafood.api.dto.input.UsuarioComSenhaInput;
import com.algaworks.algafood.api.dto.input.UsuarioInput;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.service.CadastroUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioController {

    @Autowired
    private CadastroUsuarioService usuarioService;
    @Autowired
    private UsuarioDTOAssembler usuarioDTOAssembler;
    @Autowired
    private UsuarioDTODisassembler usuarioDTODisassembler;

    @GetMapping
    public List<UsuarioDTO> listar() {
        List<Usuario> usuarios = usuarioService.listar();
        return usuarioDTOAssembler.toCollectionDTO(usuarios);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioDTO adicionar(@RequestBody @Valid UsuarioComSenhaInput usuarioInput) {
        Usuario usuario = usuarioDTODisassembler.toDomainObject(usuarioInput);
        usuario = usuarioService.salvar(usuario);
        return usuarioDTOAssembler.toDTO(usuario);
    }

    @GetMapping("/{usuarioId}")
    public UsuarioDTO buscar(@PathVariable Long usuarioId) {
        Usuario usuario = usuarioService.buscarOuFalhar(usuarioId);
        return usuarioDTOAssembler.toDTO(usuario);
    }

    @PutMapping("/{usuarioId}")
    public UsuarioDTO atualizar(
            @PathVariable Long usuarioId, @RequestBody @Valid UsuarioInput usuarioInput) {
        Usuario usuarioAtual = usuarioService.buscarOuFalhar(usuarioId);
        usuarioDTODisassembler.copyToDomainObject(usuarioInput, usuarioAtual);
        usuarioAtual = usuarioService.salvar(usuarioAtual);
        return usuarioDTOAssembler.toDTO(usuarioAtual);
    }

    @PutMapping("/{usuarioId}/senha")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizar(@PathVariable Long usuarioId, @RequestBody @Valid SenhaInput senhaInput) {
        usuarioService.alterarSenha(usuarioId, senhaInput.getSenhaAtual(), senhaInput.getNovaSenha());
    }

    @DeleteMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long usuarioId) {
        usuarioService.excluir(usuarioId);
    }

}
