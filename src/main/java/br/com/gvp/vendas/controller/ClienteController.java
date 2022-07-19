package br.com.gvp.vendas.controller;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.gvp.vendas.entity.Cliente;
import br.com.gvp.vendas.repository.ClientesRepository;

@Controller
public class ClienteController {
	
	private ClientesRepository clienteRepository;

	public ClienteController(ClientesRepository clienteRepository) {
		this.clienteRepository = clienteRepository;
	}

	@GetMapping("/api/clientes/{id}")
	@ResponseBody
	public ResponseEntity<Cliente> getClienteById(@PathVariable Integer id) {
		Optional<Cliente> cliente = clienteRepository.findById(id);
		
		if(cliente.isPresent()) {
			return ResponseEntity.ok(cliente.get());
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping("/api/clientes")
	@ResponseBody
	public ResponseEntity<Cliente> save(@RequestBody Cliente cliente) {
		Cliente clienteSalvo = clienteRepository.save(cliente);
		return ResponseEntity.ok(clienteSalvo);
	}
	
	@DeleteMapping("/api/clientes/{id}")
	@ResponseBody
	public ResponseEntity<Cliente> delete(@PathVariable Integer id) {
		Optional<Cliente> cliente = clienteRepository.findById(id);
		
		if(cliente.isPresent()) {
			clienteRepository.delete(cliente.get());
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/api/clientes/{id}")
	@ResponseBody
	public ResponseEntity<Object> update(@PathVariable Integer id, 
										  @RequestBody Cliente cliente) {
		return clienteRepository
				.findById(id)
				.map(clienteExistente -> {
					cliente.setId(clienteExistente.getId());
					clienteRepository.save(cliente);
					return ResponseEntity.noContent().build();
				}).orElseGet(() -> ResponseEntity.notFound().build());
	}
}
