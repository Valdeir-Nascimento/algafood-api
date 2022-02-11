package com.algaworks.algafood.api.v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.v1.assembler.PermissaoDTOAssembler;
import com.algaworks.algafood.api.v1.controller.swagger.GrupoPermissaoControllerSwagger;
import com.algaworks.algafood.api.v1.dto.PermissaoDTO;
import com.algaworks.algafood.api.v1.links.AlgaLinks;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.service.CadastroGrupoService;

@RestController
@RequestMapping(value = "/v1/grupos/{grupoId}/permissoes", produces = MediaType.APPLICATION_JSON_VALUE)
public class GrupoPermissaoController implements GrupoPermissaoControllerSwagger {
	@Autowired
	private CadastroGrupoService grupoService;
	@Autowired
	private PermissaoDTOAssembler permissaoDTOAssembler;
	@Autowired
	private AlgaLinks algaLinks;
	@Autowired
	private AlgaSecurity algaSecurity;

	@CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
	@Override
	@GetMapping
	public CollectionModel<PermissaoDTO> listar(@PathVariable Long grupoId) {
		Grupo grupo = grupoService.buscarOuFalhar(grupoId);

		CollectionModel<PermissaoDTO> permissoesModel = permissaoDTOAssembler.toCollectionModel(grupo.getPermissoes())
				.removeLinks();

		permissoesModel.add(algaLinks.linkToGrupoPermissoes(grupoId));

		if (algaSecurity.podeEditarUsuariosGruposPermissoes()) {
			permissoesModel.add(algaLinks.linkToGrupoPermissaoAssociacao(grupoId, "associar"));

			permissoesModel.getContent().forEach(permissaoModel -> {
				permissaoModel.add(
						algaLinks.linkToGrupoPermissaoDesassociacao(grupoId, permissaoModel.getId(), "desassociar"));
			});
		}

		return permissoesModel;
	}

	@CheckSecurity.UsuariosGruposPermissoes.PodeEditar
	@Override
	@DeleteMapping("/{permissaoId}")
	public ResponseEntity<Void> desvincular(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
		grupoService.desvincularPermissao(grupoId, permissaoId);
		return ResponseEntity.noContent().build();
	}

	@CheckSecurity.UsuariosGruposPermissoes.PodeEditar
	@Override
	@PutMapping("/{permissaoId}")
	public ResponseEntity<Void> vincular(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
		grupoService.vincularPermsissao(grupoId, permissaoId);
		return ResponseEntity.noContent().build();
	}
}
