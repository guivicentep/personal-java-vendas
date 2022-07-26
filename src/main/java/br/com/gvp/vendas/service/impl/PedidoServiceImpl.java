package br.com.gvp.vendas.service.impl;

import org.springframework.stereotype.Service;

import br.com.gvp.vendas.repository.PedidosRepository;
import br.com.gvp.vendas.service.PedidoService;

@Service
public class PedidoServiceImpl implements PedidoService{

	private PedidosRepository repository;

	public PedidoServiceImpl(PedidosRepository repository) {
		this.repository = repository;
	}
	
	
}
