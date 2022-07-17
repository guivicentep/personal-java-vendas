package br.com.gvp.vendas;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import br.com.gvp.vendas.entity.Cliente;
import br.com.gvp.vendas.repository.ClientesRepository;

@SpringBootApplication
public class VendasApplication {
	
	@Bean
	public CommandLineRunner init(@Autowired ClientesRepository clienteRepo) {
		return args -> {
			Cliente cliente = new Cliente();
			cliente.setNome("Guilherme");
			clienteRepo.salvar(cliente);
			
			Cliente cliente2 = new Cliente();
			cliente2.setNome("Jobson");
			clienteRepo.salvar(cliente2);
			
			List<Cliente> listarTodos = clienteRepo.listarTodos();
			listarTodos.forEach(System.out::println);
		};
	}
	
	public static void main(String[] args) {
		SpringApplication.run(VendasApplication.class, args);
	}

}
