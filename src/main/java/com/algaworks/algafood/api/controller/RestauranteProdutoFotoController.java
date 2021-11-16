package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.FotoProdutoDTOAssembler;
import com.algaworks.algafood.api.dto.FotoProdutoDTO;
import com.algaworks.algafood.api.dto.FotoRecuperadaDTO;
import com.algaworks.algafood.api.dto.input.FotoProdutoInput;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.service.CadastroProdutoService;
import com.algaworks.algafood.domain.service.CatalogoFotoProdutoService;
import com.algaworks.algafood.domain.service.FotoStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoController {

    @Autowired
    private CatalogoFotoProdutoService catalogoFotoProdutoService;
    @Autowired
    private CadastroProdutoService produtoService;
    @Autowired
    private FotoProdutoDTOAssembler fotoProdutoDTOAssembler;

    @Qualifier("storageNuvem")
    @Autowired
    private FotoStorageService fotoStorageService;

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FotoProdutoDTO atualizarFoto(
            @PathVariable Long restauranteId,
            @PathVariable Long produtoId,
            @Valid FotoProdutoInput fotoProdutoInput) throws IOException {

        Produto produto = produtoService.buscarOuFalhar(restauranteId, produtoId);
        FotoProduto foto = new FotoProduto();
        foto.setProduto(produto);
        foto.setDescricao(fotoProdutoInput.getDescricao());
        foto.setContentType(fotoProdutoInput.getArquivo().getContentType());
        foto.setTamanho(fotoProdutoInput.getArquivo().getSize());
        foto.setNomeArquivo(fotoProdutoInput.getArquivo().getName());

        FotoProduto fotoSalva = catalogoFotoProdutoService.salvar(foto, fotoProdutoInput.getArquivo().getInputStream());
        return fotoProdutoDTOAssembler.toDTO(fotoSalva);
    }

    @GetMapping
    public FotoProdutoDTO buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        FotoProduto fotoProduto = catalogoFotoProdutoService.buscarOuFalhar(restauranteId, produtoId);
        return fotoProdutoDTOAssembler.toDTO(fotoProduto);
    }

    @GetMapping(produces = MediaType.ALL_VALUE)
    public ResponseEntity<?> servirFoto(
            @PathVariable Long restauranteId,
            @PathVariable Long produtoId,
            @RequestHeader(name = "accept") String acceptHeader) throws HttpMediaTypeNotAcceptableException {

        try {
            FotoProduto fotoProduto = catalogoFotoProdutoService.buscarOuFalhar(restauranteId, produtoId);
            MediaType mediaTypeFoto = MediaType.parseMediaType(fotoProduto.getContentType());
            List<MediaType> mediaTypeList = MediaType.parseMediaTypes(acceptHeader);

            verificarCompatibilidadeMediaType(mediaTypeFoto, mediaTypeList);
            FotoRecuperadaDTO fotoRecuperadaDTO = fotoStorageService.recuperar(fotoProduto.getNomeArquivo());

            if (fotoRecuperadaDTO.temUrl()) {
                return ResponseEntity.status(HttpStatus.FOUND)
                        .header(HttpHeaders.LOCATION, fotoRecuperadaDTO.getUrl())
                        .build();
            } else {
                return ResponseEntity.ok()
                        .contentType(mediaTypeFoto)
                        .body(new InputStreamResource(fotoRecuperadaDTO.getInputStream()));

            }

        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        catalogoFotoProdutoService.excluir(restauranteId, produtoId);
    }

    private void verificarCompatibilidadeMediaType(MediaType mediaTypeFoto, List<MediaType> mediaTypeList) throws HttpMediaTypeNotAcceptableException {
        boolean compativel = mediaTypeList.stream()
                .anyMatch(mediaTypeAceita -> mediaTypeAceita.isCompatibleWith(mediaTypeFoto));
        if (!compativel) {
            throw new HttpMediaTypeNotAcceptableException(mediaTypeList);
        }
    }

}
