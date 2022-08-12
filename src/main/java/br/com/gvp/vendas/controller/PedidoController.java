package br.com.gvp.vendas.controller;

import static org.springframework.http.HttpStatus.*;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.gvp.vendas.dto.AtualizacaoStatusPedidoDTO;
import br.com.gvp.vendas.dto.DetalhesPedidoDTO;
import br.com.gvp.vendas.dto.InformacoesItemPedidoDTO;
import br.com.gvp.vendas.dto.PedidoDTO;
import br.com.gvp.vendas.entity.ItemPedido;
import br.com.gvp.vendas.entity.Pedido;
import br.com.gvp.vendas.enums.StatusPedido;
import br.com.gvp.vendas.service.PedidoService;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {
	
	private PedidoService service;

	public PedidoController(PedidoService service) {
		this.service = service;
	}

	@PostMapping
	@ResponseStatus(CREATED)
	public Integer save(@RequestBody PedidoDTO dto) {
		Pedido pedido = service.salvar(dto);
		return pedido.getId();
	}
	
	@GetMapping("{id}")
	public DetalhesPedidoDTO getById(@PathVariable Integer id) {
		return service
				.obterPedidoCompleto(id)
				.map(p -> converter(p))
				.orElseThrow(() ->
						new ResponseStatusException(NOT_FOUND, "Pedido não encontrado."));
	}
	
	@PatchMapping("{id}")
	@ResponseStatus(NO_CONTENT)
	public void updateStatus(@PathVariable Integer id,
							 @RequestBody AtualizacaoStatusPedidoDTO dto) {
		String novoStatus = dto.getNovoStatus();
		service.atualizaStatus(id, StatusPedido.valueOf(novoStatus));
	}
	
	private DetalhesPedidoDTO converter(Pedido pedido) {
		return DetalhesPedidoDTO
			.builder()
			.codigo(pedido.getId())
			.dataPedido(pedido.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
			.cpf(pedido.getCliente().getCpf())
			.nomeCliente(pedido.getCliente().getNome())
			.total(pedido.getTotal())
			.status(pedido.getStatus().name())
			.itens(converter(pedido.getItens()))
			.build();
	}
	
	private List<InformacoesItemPedidoDTO> converter(List<ItemPedido> itens) {
		if(CollectionUtils.isEmpty(itens)) {
			return Collections.emptyList();
		}
		
		return itens.stream().map(
				item -> InformacoesItemPedidoDTO
					.builder().descricaoProduto(item.getProduto().getDescricao())
					.precoUnitario(item.getProduto().getPreco())
					.quantidade(item.getQuantidade())
					.build()
		).collect(Collectors.toList());
	}
}
