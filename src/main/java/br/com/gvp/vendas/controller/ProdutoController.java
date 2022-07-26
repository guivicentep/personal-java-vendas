package br.com.gvp.vendas.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

import java.util.List;

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

import br.com.gvp.vendas.entity.Produto;
import br.com.gvp.vendas.repository.ProdutosRepository;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {
	
	private ProdutosRepository produtoRepository;

	public ProdutoController(ProdutosRepository produtoRepository) {
		this.produtoRepository = produtoRepository;
	}
	
	@PostMapping
	@ResponseStatus(CREATED)
	public Produto save(@RequestBody Produto produto) {
		return produtoRepository.save(produto);
	}
	
	@PutMapping("{id}")
	@ResponseStatus(NO_CONTENT)
	public void update(@PathVariable Integer id, @RequestBody Produto produto) {
		produtoRepository.findById(id).map(p -> {
			produto.setId(p.getId());
			produtoRepository.save(produto);
			return produto;
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado!!"));
	}
	
	@DeleteMapping("{id}")
	@ResponseStatus(NO_CONTENT)
	public void delete(@PathVariable Integer id) {
		produtoRepository.findById(id).map(p -> {
			produtoRepository.deleteById(id);
			return Void.TYPE;
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado!!"));
	}
	
	@GetMapping("{id}")
	public Produto getById(@PathVariable Integer id) {
		return produtoRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado!!"));
	}
	
	
	@GetMapping
	public List<Produto> find(Produto filter) {
		ExampleMatcher matcher = ExampleMatcher
								.matching()
								.withIgnoreCase()
								.withStringMatcher(StringMatcher.CONTAINING);
		
		Example<Produto> example = Example.of(filter, matcher);
		return produtoRepository.findAll(example);
	}
}
