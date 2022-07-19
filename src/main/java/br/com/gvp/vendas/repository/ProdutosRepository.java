package br.com.gvp.vendas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gvp.vendas.entity.Produto;

public interface ProdutosRepository extends JpaRepository<Produto, Integer> {

}
