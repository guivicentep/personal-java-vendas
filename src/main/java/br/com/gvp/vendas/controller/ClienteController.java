package br.com.gvp.vendas.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.gvp.vendas.entity.Cliente;
import br.com.gvp.vendas.repository.ClientesRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/clientes")
@Api("Api de Clientes")
public class ClienteController {
	
	private ClientesRepository clienteRepository;

	public ClienteController(ClientesRepository clienteRepository) {
		this.clienteRepository = clienteRepository;
	}

	@GetMapping("{id}")
	@ApiOperation("Obter detalhes de um Cliente")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Cliente encontrado"),
		@ApiResponse(code = 404, message = "Cliente não encontrado para o ID informado")
	})
	public Cliente getClienteById(@PathVariable 
			@ApiParam("Id do cliente") Integer id) {
		return clienteRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
		
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation("Salva um novo cliente")
	@ApiResponses({
		@ApiResponse(code = 201, message = "Cliente salvo com sucesso"),
		@ApiResponse(code = 400, message = "Erro de validação")
	})
	public Cliente save(@RequestBody @Valid Cliente cliente) {
		return clienteRepository.save(cliente);
	}
	
	 @DeleteMapping("{id}")
	 @ResponseStatus(HttpStatus.NO_CONTENT)
	 public void delete(@PathVariable Integer id){
		 clienteRepository.findById(id)
		 					.map(cliente  -> {
		 						clienteRepository.delete(cliente);
		 						return cliente;
		 					})
		 					.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
		 							"Cliente não encontrado!!"));
	 }
	
	@PutMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update(@PathVariable Integer id, 
										  @RequestBody @Valid Cliente cliente) {
		clienteRepository
				.findById(id)
				.map(clienteExistente -> {
					cliente.setId(clienteExistente.getId());
					clienteRepository.save(cliente);
					return clienteExistente;
				}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
	}
	
	@GetMapping
	public List<Cliente> find(Cliente filter) {
		ExampleMatcher matcher = ExampleMatcher
								.matching()
								.withIgnoreCase()
								.withStringMatcher(StringMatcher.CONTAINING);
		
		Example<Cliente> example = Example.of(filter, matcher);
		return clienteRepository.findAll(example);
	}
}
