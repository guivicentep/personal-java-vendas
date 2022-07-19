package br.com.gvp.vendas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.gvp.vendas.entity.Cliente;

public interface ClientesRepository extends JpaRepository<Cliente, Integer>{
	
	List<Object> findByNomeLike(String nome);
	
	@Query(" delete from Cliente c where c.nome = :nome ")
	@Modifying
	void deleteByNome(String nome);
	
	@Query(" select c from Cliente c left join fetch c.pedidos where c.id = :id")
	Cliente findClienteFetchPedidos(@Param("id") Integer id);
	
}
