package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.RestauranteApenasNomeDTOAssembler;
import com.algaworks.algafood.api.assembler.RestauranteBasicoDTOAssembler;
import com.algaworks.algafood.api.assembler.RestauranteDTOAssembler;
import com.algaworks.algafood.api.assembler.RestauranteInputDisassembler;
import com.algaworks.algafood.api.controller.swagger.RestauranteControllerSwagger;
import com.algaworks.algafood.api.dto.RestauranteBasicoDTO;
import com.algaworks.algafood.api.dto.RestauranteDTO;
import com.algaworks.algafood.api.dto.input.RestauranteInput;
import com.algaworks.algafood.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/restaurantes", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteController implements RestauranteControllerSwagger {

    @Autowired
    private CadastroRestauranteService restauranteService;
    
    @Autowired
    private RestauranteDTOAssembler restauranteDTOAssembler;
    
    @Autowired
    private RestauranteInputDisassembler restauranteInputDisassembler;

    @Autowired
    private RestauranteBasicoDTOAssembler restauranteBasicoDTOAssembler;

    @Autowired
    private RestauranteApenasNomeDTOAssembler restauranteApenasNomeDTOAssembler;



//    @JsonView(RestauranteView.Resumo.class)
    @Override
    @GetMapping
    public CollectionModel<RestauranteBasicoDTO> listar() {
        return restauranteBasicoDTOAssembler.toCollectionModel(restauranteService.findAll());
    }

    @GetMapping("/{restauranteId}")
    public RestauranteDTO buscar(@PathVariable("restauranteId") Long restauranteId) {
        Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);
        return restauranteDTOAssembler.toModel(restaurante);
    }

    @Override
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public RestauranteDTO adicionar(@RequestBody @Valid RestauranteInput restauranteInput) {
        try {
            Restaurante restaurante = restauranteInputDisassembler.toDomainObject(restauranteInput);
            return restauranteDTOAssembler.toModel(restauranteService.salvar(restaurante));
        } catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @Override
    @PutMapping("/{restauranteId}")
    public RestauranteDTO atualizar(@PathVariable Long restauranteId, @RequestBody @Valid RestauranteInput restauranteInput) {
        Restaurante restauranteAtual = restauranteService.buscarOuFalhar(restauranteId);
        try {
            restauranteInputDisassembler.copyToDomainObject(restauranteInput, restauranteAtual);
            return restauranteDTOAssembler.toModel(restauranteService.salvar(restauranteAtual));
        } catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @Override
    @PutMapping("/{restauranteId}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> ativar(@PathVariable Long restauranteId) {
        restauranteService.ativar(restauranteId);
        return ResponseEntity.noContent().build();
    }

    @Override
    @PutMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativarMultiplos(@RequestBody List<Long> restaurantesIds) {
        try {
    	    restauranteService.ativar(restaurantesIds);
        } catch (RestauranteNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @Override
    @DeleteMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inativarMultiplos(@RequestBody List<Long> restaurantesIds) {
    	try {			
    		restauranteService.inativar(restaurantesIds);
		} catch (RestauranteNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
    }

    @Override
    @DeleteMapping("/{restauranteId}/inativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> inativar(@PathVariable Long restauranteId) {
        restauranteService.inativar(restauranteId);
        return ResponseEntity.noContent().build();
    }

    @Override
    @PutMapping("/{restauranteId}/abertura")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> abrir(Long restauranteId) {
        restauranteService.abrir(restauranteId);
        return ResponseEntity.noContent().build();
    }

    @Override
    @PutMapping("/{restauranteId}/fechamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> fechar(Long restauranteId) {
        restauranteService.fechar(restauranteId);
        return ResponseEntity.noContent().build();
    }

}
