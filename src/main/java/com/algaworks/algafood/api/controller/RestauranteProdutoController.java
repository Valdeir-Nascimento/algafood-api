package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.ProdutoDTOAssembler;
import com.algaworks.algafood.api.assembler.ProdutoInputDTODisassembler;
import com.algaworks.algafood.api.controller.swagger.RestauranteProdutoControllerSwagger;
import com.algaworks.algafood.api.dto.ProdutoDTO;
import com.algaworks.algafood.api.dto.input.ProdutoInput;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import com.algaworks.algafood.domain.service.CadastroProdutoService;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/restaurantes/{restauranteId}/produtos", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteProdutoController implements RestauranteProdutoControllerSwagger {

    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private CadastroProdutoService produtoService;
    @Autowired
    private CadastroRestauranteService restauranteService;
    @Autowired
    private ProdutoDTOAssembler produtoDTOAssembler;
    @Autowired
    private ProdutoInputDTODisassembler produtoInputDisassembler;

    @GetMapping
    public List<ProdutoDTO> listar(@RequestParam(required = false) boolean incluirInativos, @PathVariable Long restauranteId) {
        Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);
        List<Produto> produtos;
        if (incluirInativos) {
            produtos = produtoRepository.findTodosByRestaurante(restaurante);
        } else {
            produtos = produtoRepository.findByAtivosByRestaurante(restaurante);
        }
        return produtoDTOAssembler.toCollectionDTO(produtos);
    }

    @GetMapping("/{produtoId}")
    public ProdutoDTO buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        Produto produto = produtoService.buscarOuFalhar(restauranteId, produtoId);
        return produtoDTOAssembler.toDTO(produto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoDTO adicionar(
            @PathVariable Long restauranteId, @RequestBody @Valid ProdutoInput produtoInput) {
        Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);
        Produto produto = produtoInputDisassembler.toDomainObject(produtoInput);
        produto.setRestaurante(restaurante);
        produto = produtoService.salvar(produto);
        return produtoDTOAssembler.toDTO(produto);
    }

    /**
     * /restaurantes/{restauranteId}/produtos/produtoId
     */
    @PutMapping("/{produtoId}")
    public ProdutoDTO atualizar(
            @PathVariable Long restauranteId,
            @PathVariable Long produtoId,
            @RequestBody @Valid ProdutoInput produtoInput) {
        Produto produtoAtual = produtoService.buscarOuFalhar(restauranteId, produtoId);
        produtoInputDisassembler.copyToDomainObject(produtoInput, produtoAtual);
        produtoAtual = produtoService.salvar(produtoAtual);
        return produtoDTOAssembler.toDTO(produtoAtual);
    }

}
