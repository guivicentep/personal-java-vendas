package br.com.gvp.vendas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gvp.vendas.entity.ItemPedido;

public interface ItensPedidoRepository extends JpaRepository<ItemPedido, Integer>{

}
