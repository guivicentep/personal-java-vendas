package br.com.gvp.vendas.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import br.com.gvp.vendas.entity.Cliente;

@Repository
public class ClientesRepository {
	
	private static String INSERT = "insert into cliente (nome) values (?) ";
	private static String SELECT_ALL = "select * from cliente";

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public Cliente salvar(Cliente cliente) {
		jdbcTemplate.update(INSERT ,new Object[] {cliente.getNome()});
		return cliente;
	}
	
	public List<Cliente> listarTodos() {
		return jdbcTemplate.query(SELECT_ALL, new RowMapper<Cliente>() {
		@Override
		public Cliente mapRow(ResultSet resultSet, int i) throws SQLException {
			Integer id = resultSet.getInt("id");
			String nome = resultSet.getString("nome");
			return new Cliente(id, nome);
		}
		
		});
	}
}
