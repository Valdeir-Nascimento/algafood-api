package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.GrupoDTOAssembler;
import com.algaworks.algafood.api.assembler.GrupoDTODisassembler;
import com.algaworks.algafood.api.dto.GrupoDTO;
import com.algaworks.algafood.api.dto.input.GrupoInput;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.service.CadastroGrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/grupos")
public class GrupoController {

    @Autowired
    private CadastroGrupoService grupoService;
    @Autowired
    private GrupoDTOAssembler grupoDTOAssembler;
    @Autowired
    private GrupoDTODisassembler grupoDTODisassembler;

    @GetMapping
    public List<GrupoDTO> listar() {
        return grupoDTOAssembler.toCollectionDTO(grupoService.listar());
    }

    @GetMapping("/{grupoId}")
    public GrupoDTO buscar(@PathVariable Long grupoId) {
        Grupo grupo = grupoService.buscarOuFalhar(grupoId);
        return grupoDTOAssembler.toDTO(grupo);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GrupoDTO adicionar(@RequestBody @Valid GrupoInput grupoInput) {
        Grupo grupo = grupoDTODisassembler.toDomainObject(grupoInput);
        grupo = grupoService.salvar(grupo);
        return grupoDTOAssembler.toDTO(grupo);
    }

    @PutMapping("/{grupoId}")
    public GrupoDTO atualizar(@PathVariable Long grupoId, @RequestBody @Valid GrupoInput grupoInput) {
        Grupo grupo = grupoService.buscarOuFalhar(grupoId);
        grupoDTODisassembler.copyToDomainObject(grupoInput, grupo);
        grupo = grupoService.salvar(grupo);
        return grupoDTOAssembler.toDTO(grupo);
    }

    @DeleteMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long grupoId) {
        grupoService.excluir(grupoId);
    }

}
