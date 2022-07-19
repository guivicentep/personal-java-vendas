package br.com.gvp.vendas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gvp.vendas.entity.Cliente;
import br.com.gvp.vendas.entity.Pedido;

public interface PedidosRepository extends JpaRepository<Pedido, Integer>{
	List<Pedido> findByCliente(Cliente cliente);
}
