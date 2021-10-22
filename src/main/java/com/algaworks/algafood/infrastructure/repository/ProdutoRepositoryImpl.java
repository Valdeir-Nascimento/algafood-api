package com.algaworks.algafood.infrastructure.repository;

import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.repository.ProdutoRepositoryQueries;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class ProdutoRepositoryImpl implements ProdutoRepositoryQueries {

    @PersistenceContext
    EntityManager manager;

    @Transactional
    @Override
    public FotoProduto save(FotoProduto fotoProduto) {
        return manager.merge(fotoProduto);
    }
}
