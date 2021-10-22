package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CatalogoFotoProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Transactional
    public FotoProduto salvar(FotoProduto foto) {
        //excluir foto, se existir
        Optional<FotoProduto> fotoExistente = produtoRepository.findFotoById(foto.getRestauranteId(), foto.getProduto().getId());
        if (fotoExistente.isPresent()) {
            produtoRepository.delete(fotoExistente.get());
        }
        return produtoRepository.save(foto);
    }

}
