package br.com.gvp.vendas.service;

import java.util.Optional;

import br.com.gvp.vendas.dto.PedidoDTO;
import br.com.gvp.vendas.entity.Pedido;
import br.com.gvp.vendas.enums.StatusPedido;

public interface PedidoService {
	Pedido salvar(PedidoDTO dto);
	
	Optional<Pedido> obterPedidoCompleto(Integer id);
	
	void atualizaStatus(Integer id, StatusPedido statusPedido);
}
