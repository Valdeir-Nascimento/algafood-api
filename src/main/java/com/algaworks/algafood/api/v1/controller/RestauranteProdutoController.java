package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.assembler.ProdutoDTOAssembler;
import com.algaworks.algafood.api.v1.assembler.ProdutoInputDTODisassembler;
import com.algaworks.algafood.api.v1.controller.swagger.RestauranteProdutoControllerSwagger;
import com.algaworks.algafood.api.v1.dto.ProdutoDTO;
import com.algaworks.algafood.api.v1.dto.input.ProdutoInput;
import com.algaworks.algafood.api.v1.links.AlgaLinks;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import com.algaworks.algafood.domain.service.CadastroProdutoService;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/v1/restaurantes/{restauranteId}/produtos", produces = MediaType.APPLICATION_JSON_VALUE)
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
    @Autowired
    private AlgaLinks algaLinks;

    @CheckSecurity.Restaurantes.PodeConsultar
    @GetMapping
    public CollectionModel<ProdutoDTO> listar(@RequestParam(required = false) Boolean incluirInativos, @PathVariable Long restauranteId) {
        Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);
        List<Produto> todosProdutos = null;

        if (incluirInativos) {
            todosProdutos = produtoRepository.findTodosByRestaurante(restaurante);
        } else {
            todosProdutos = produtoRepository.findByAtivosByRestaurante(restaurante);
        }
        return produtoDTOAssembler.toCollectionModel(todosProdutos).add(algaLinks.linkToProdutos(restauranteId));
    }

    @CheckSecurity.Restaurantes.PodeConsultar
    @GetMapping("/{produtoId}")
    public ProdutoDTO buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        Produto produto = produtoService.buscarOuFalhar(restauranteId, produtoId);
        return produtoDTOAssembler.toModel(produto);
    }

    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoDTO adicionar(
            @PathVariable Long restauranteId, @RequestBody @Valid ProdutoInput produtoInput) {
        Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);
        Produto produto = produtoInputDisassembler.toDomainObject(produtoInput);
        produto.setRestaurante(restaurante);
        produto = produtoService.salvar(produto);
        return produtoDTOAssembler.toModel(produto);
    }

    @CheckSecurity.Restaurantes.PodeGerenciarCadastro
    @PutMapping("/{produtoId}")
    public ProdutoDTO atualizar(
            @PathVariable Long restauranteId,
            @PathVariable Long produtoId,
            @RequestBody @Valid ProdutoInput produtoInput) {
        Produto produtoAtual = produtoService.buscarOuFalhar(restauranteId, produtoId);
        produtoInputDisassembler.copyToDomainObject(produtoInput, produtoAtual);
        produtoAtual = produtoService.salvar(produtoAtual);
        return produtoDTOAssembler.toModel(produtoAtual);
    }

}
