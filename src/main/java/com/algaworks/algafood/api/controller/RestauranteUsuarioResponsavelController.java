package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.UsuarioDTOAssembler;
import com.algaworks.algafood.api.controller.swagger.RestauranteUsuarioResponsavelControllerSwagger;
import com.algaworks.algafood.api.dto.UsuarioDTO;
import com.algaworks.algafood.api.links.AlgaLinks;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/restaurantes/{restauranteId}/responsaveis", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteUsuarioResponsavelController implements RestauranteUsuarioResponsavelControllerSwagger {

	@Autowired
	private CadastroRestauranteService restauranteService;

	@Autowired
	private UsuarioDTOAssembler usuarioDTOAssembler;

	@Autowired
	private AlgaLinks algaLinks;

	@GetMapping
	public CollectionModel<UsuarioDTO> listar(@PathVariable Long restauranteId) {
		Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);
		return usuarioDTOAssembler
				.toCollectionModel(restaurante.getResponsaveis())
				.removeLinks()
				.add(algaLinks.linkToResponsaveisRestaurante(restauranteId));

	}

	@DeleteMapping("/{usuarioId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void desassociar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
		restauranteService.desvincularResponsavel(restauranteId, usuarioId);
	}

	@PutMapping("/{usuarioId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void associar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
		restauranteService.vincularResponsavel(restauranteId, usuarioId);
	}

}
