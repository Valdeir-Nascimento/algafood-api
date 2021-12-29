package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.GrupoDTOAssembler;
import com.algaworks.algafood.api.controller.swagger.UsuarioGrupoControllerSwagger;
import com.algaworks.algafood.api.dto.GrupoDTO;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.service.CadastroUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/usuarios/{usuarioId}/grupos",  produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioGrupoController implements UsuarioGrupoControllerSwagger {
    @Autowired
    private CadastroUsuarioService usuarioService;
    @Autowired
    private GrupoDTOAssembler grupoDTOAssembler;

    @GetMapping
    public CollectionModel<GrupoDTO> listar(@PathVariable Long usuarioId) {
        Usuario usuario = usuarioService.buscarOuFalhar(usuarioId);
        return grupoDTOAssembler.toCollectionModel(usuario.getGrupos());
    }

    @DeleteMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desvincular(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
        usuarioService.desvincularGrupo(usuarioId, grupoId);
    }

    @PutMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void vincular(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
        usuarioService.vincularGrupo(usuarioId, grupoId);
    }

}
