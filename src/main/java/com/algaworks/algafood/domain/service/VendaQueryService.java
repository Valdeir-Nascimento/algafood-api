package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.api.dto.VendaDiariaDTO;
import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface VendaQueryService {
    List<VendaDiariaDTO> consultarVendasDiarias(VendaDiariaFilter filtro);
}
