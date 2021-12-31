package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.assembler.GrupoDTOAssembler;
import com.algaworks.algafood.api.v1.controller.swagger.UsuarioGrupoControllerSwagger;
import com.algaworks.algafood.api.v1.dto.GrupoDTO;
import com.algaworks.algafood.api.v1.links.AlgaLinks;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.service.CadastroUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/usuarios/{usuarioId}/grupos", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioGrupoController implements UsuarioGrupoControllerSwagger {
    @Autowired
    private CadastroUsuarioService usuarioService;
    @Autowired
    private GrupoDTOAssembler grupoDTOAssembler;
    @Autowired
    private AlgaLinks algaLinks;

    @Override
    @GetMapping
    public CollectionModel<GrupoDTO> listar(@PathVariable Long usuarioId) {
        Usuario usuario = usuarioService.buscarOuFalhar(usuarioId);
        CollectionModel<GrupoDTO> gruposModel = grupoDTOAssembler.toCollectionModel(usuario.getGrupos())
                .removeLinks()
                .add(algaLinks.linkToUsuarioGrupoAssociacao(usuarioId, "associar"));

        gruposModel.getContent().forEach(grupoModel -> {
            grupoModel.add(algaLinks.linkToUsuarioGrupoDesassociacao(
                    usuarioId, grupoModel.getId(), "desassociar"));
        });

        return gruposModel;
    }

    @Override
    @DeleteMapping("/{grupoId}")
    public ResponseEntity<Void> desvincular(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
        usuarioService.desvincularGrupo(usuarioId, grupoId);
        return ResponseEntity.noContent().build();
    }

    @Override
    @PutMapping("/{grupoId}")
    public ResponseEntity<Void> vincular(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
        usuarioService.vincularGrupo(usuarioId, grupoId);
        return ResponseEntity.noContent().build();
    }

}
