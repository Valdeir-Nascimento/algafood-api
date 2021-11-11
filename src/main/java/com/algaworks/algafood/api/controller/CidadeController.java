package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.CidadeDTOAssembler;
import com.algaworks.algafood.api.assembler.CidadeInputDisassembler;
import com.algaworks.algafood.api.dto.CidadeDTO;
import com.algaworks.algafood.api.dto.input.CidadeInput;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CadastroCidadeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Cidades")
@RestController
@RequestMapping("/cidades")
public class CidadeController {

    @Autowired
    private CadastroCidadeService cidadeService;
    @Autowired
    private CidadeRepository cidadeRepository;
    @Autowired
    private CidadeDTOAssembler cidadeDTOAssembler;
    @Autowired
    private CidadeInputDisassembler cidadeInputDisassembler;

    @ApiOperation("Lista as cidades")
    @GetMapping
    public List<CidadeDTO> listar() {
        return cidadeDTOAssembler.toCollectionDTO(cidadeRepository.findAll());
    }

    @ApiOperation("Cadastra uma cidade")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CidadeDTO adicionar(@ApiParam(name = "corpo", value = "Representação de uma nova cidade") @RequestBody @Valid CidadeInput cidadeInput) {
        try {
            Cidade cidade = cidadeInputDisassembler.toDomainObject(cidadeInput);
            cidade = cidadeService.salvar(cidade);
            return cidadeDTOAssembler.toDTO(cidade);
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @ApiOperation("Busca uma cidade por ID")
    @GetMapping("/{cidadeId}")
    public CidadeDTO buscar(@ApiParam(value = "ID de uma cidade", example = "1") @PathVariable("cidadeId") Long cidadeId) {
        return cidadeDTOAssembler.toDTO(cidadeService.buscarOuFalhar(cidadeId));
    }

    @ApiOperation("Atualiza uma cidade por ID")
    @PutMapping("/{cidadeId}")
    public CidadeDTO atualizar(
            @PathVariable("cidadeId") Long cidadeId,
            @ApiParam(name = "corpo", value = "ID de uma cidade", example = "1")
            @RequestBody @Valid CidadeInput cidadeInput) {
        Cidade cidadeAtual = cidadeService.buscarOuFalhar(cidadeId);
        try {
            cidadeInputDisassembler.copyDomainObject(cidadeInput, cidadeAtual);
            cidadeAtual = cidadeService.salvar(cidadeAtual);
            return cidadeDTOAssembler.toDTO(cidadeAtual);
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @ApiOperation("Exclui uma cidade por ID")
    @DeleteMapping("/{cidadeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(
            @ApiParam(name = "corpo", value = "ID de uma cidade", example = "1")
            @PathVariable Long cidadeId) {
        cidadeService.excluir(cidadeId);
    }

}
