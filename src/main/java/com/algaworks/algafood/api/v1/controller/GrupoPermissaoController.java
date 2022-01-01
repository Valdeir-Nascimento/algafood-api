package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.assembler.PermissaoDTOAssember;
import com.algaworks.algafood.api.v1.controller.swagger.GrupoPermissaoControllerSwagger;
import com.algaworks.algafood.api.v1.dto.PermissaoDTO;
import com.algaworks.algafood.api.v1.links.AlgaLinks;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.service.CadastroGrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/grupos/{grupoId}/permissoes",  produces = MediaType.APPLICATION_JSON_VALUE)
public class GrupoPermissaoController implements GrupoPermissaoControllerSwagger {
    @Autowired
    private CadastroGrupoService grupoService;
    @Autowired
    private PermissaoDTOAssember permissaoDTOAssember;
    @Autowired
    private AlgaLinks algaLinks;

    @Override
    @GetMapping
    public CollectionModel<PermissaoDTO> listar(@PathVariable Long grupoId) {
        Grupo grupo = grupoService.buscarOuFalhar(grupoId);

        CollectionModel<PermissaoDTO> permissoesModel
                = permissaoDTOAssember.toCollectionModel(grupo.getPermissoes())
                .removeLinks()
                .add(algaLinks.linkToGrupoPermissoes(grupoId))
                .add(algaLinks.linkToGrupoPermissaoAssociacao(grupoId, "associar"));

        permissoesModel.getContent().forEach(permissaoModel -> {
            permissaoModel.add(algaLinks.linkToGrupoPermissaoDesassociacao(
                    grupoId, permissaoModel.getId(), "desassociar"));
        });

        return permissoesModel;
    }

    @Override
    @DeleteMapping("/{permissaoId}")
    public ResponseEntity<Void> desvincular(@PathVariable Long grupoId, @PathVariable Long permissaoId){
        grupoService.desvincularPermissao(grupoId, permissaoId);
        return ResponseEntity.noContent().build();
    }

    @Override
    @PutMapping("/{permissaoId}")
    public ResponseEntity<Void> vincular(@PathVariable Long grupoId, @PathVariable Long permissaoId){
        grupoService.vincularPermsissao(grupoId, permissaoId);
        return ResponseEntity.noContent().build();
    }
}
