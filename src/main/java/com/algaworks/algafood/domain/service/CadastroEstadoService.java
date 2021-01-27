package com.algaworks.algafood.domain.service;

import com.sun.xml.bind.v2.runtime.reflect.ListTransducedAccessorImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CadastroEstadoService {

	@Autowired
	private EstadoRepository estadoRepository;

	public Estado save(Estado estado) {
		return estadoRepository.save(estado);
	}

	public Estado findById(Long estadoId) {
		Optional<Estado> estado = estadoRepository.findById(estadoId);
		return estado.get();
	}

	public List<Estado> findAll() {
		return estadoRepository.findAll();
	}

	 public void deleteById(Long estadoId) {
         try {
         	 estadoRepository.findById(estadoId);
         } catch (EmptyResultDataAccessException e) {
             throw new EntidadeNaoEncontradaException(
                 String.format("Não existe um cadastro de estado com código %d", estadoId));
         
         } catch (DataIntegrityViolationException e) {
             throw new EntidadeEmUsoException(
                 String.format("Estado de código %d não pode ser removido, pois está em uso", estadoId));
         }
     }
	
}
