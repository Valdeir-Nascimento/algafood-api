package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.assembler.GrupoDTOAssembler;
import com.algaworks.algafood.api.v1.assembler.GrupoDTODisassembler;
import com.algaworks.algafood.api.v1.controller.swagger.GrupoControllerSwagger;
import com.algaworks.algafood.api.v1.dto.GrupoDTO;
import com.algaworks.algafood.api.v1.dto.input.GrupoInput;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.service.CadastroGrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/grupos", produces = MediaType.APPLICATION_JSON_VALUE)
public class GrupoController implements GrupoControllerSwagger {

    @Autowired
    private CadastroGrupoService grupoService;
    @Autowired
    private GrupoDTOAssembler grupoDTOAssembler;
    @Autowired
    private GrupoDTODisassembler grupoDTODisassembler;

    @Override
    @GetMapping
    public CollectionModel<GrupoDTO> listar() {
        return grupoDTOAssembler.toCollectionModel(grupoService.listar());
    }

    @Override
    @GetMapping("/{grupoId}")
    public GrupoDTO buscar(@PathVariable Long grupoId) {
        Grupo grupo = grupoService.buscarOuFalhar(grupoId);
        return grupoDTOAssembler.toModel(grupo);
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GrupoDTO adicionar(@RequestBody @Valid GrupoInput grupoInput) {
        Grupo grupo = grupoDTODisassembler.toDomainObject(grupoInput);
        grupo = grupoService.salvar(grupo);
        return grupoDTOAssembler.toModel(grupo);
    }

    @Override
    @PutMapping("/{grupoId}")
    public GrupoDTO atualizar(@PathVariable Long grupoId, @RequestBody @Valid GrupoInput grupoInput) {
        Grupo grupo = grupoService.buscarOuFalhar(grupoId);
        grupoDTODisassembler.copyToDomainObject(grupoInput, grupo);
        grupo = grupoService.salvar(grupo);
        return grupoDTOAssembler.toModel(grupo);
    }

    @DeleteMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long grupoId) {
        grupoService.excluir(grupoId);
    }

}
