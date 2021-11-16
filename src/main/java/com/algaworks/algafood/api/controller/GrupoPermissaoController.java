package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.PermissaoDTOAssember;
import com.algaworks.algafood.api.controller.swagger.GrupoPermissaoControllerSwagger;
import com.algaworks.algafood.api.dto.PermissaoDTO;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.service.CadastroGrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/grupos/{grupoId}/permissoes",  produces = MediaType.APPLICATION_JSON_VALUE)
public class GrupoPermissaoController implements GrupoPermissaoControllerSwagger {
    @Autowired
    private CadastroGrupoService grupoService;
    @Autowired
    private PermissaoDTOAssember permissaoDTOAssember;

    @GetMapping
    public List<PermissaoDTO> listar(@PathVariable Long grupoId) {
        Grupo grupo = grupoService.buscarOuFalhar(grupoId);
        return permissaoDTOAssember.toCollectionModel(grupo.getPermisoes());
    }

    @DeleteMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desvincular(@PathVariable Long grupoId, @PathVariable Long permissaoId){
        grupoService.desvincularPermissao(grupoId, permissaoId);
    }

    @PutMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void vincular(@PathVariable Long grupoId, @PathVariable Long permissaoId){
        grupoService.vincularPermsissao(grupoId, permissaoId);
    }
}
