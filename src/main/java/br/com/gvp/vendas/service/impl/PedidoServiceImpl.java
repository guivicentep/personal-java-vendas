package br.com.gvp.vendas.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.gvp.vendas.dto.ItemPedidoDTO;
import br.com.gvp.vendas.dto.PedidoDTO;
import br.com.gvp.vendas.entity.Cliente;
import br.com.gvp.vendas.entity.ItemPedido;
import br.com.gvp.vendas.entity.Pedido;
import br.com.gvp.vendas.entity.Produto;
import br.com.gvp.vendas.enums.StatusPedido;
import br.com.gvp.vendas.exception.PedidoNaoEncontradoException;
import br.com.gvp.vendas.exception.RegraNegocioException;
import br.com.gvp.vendas.repository.ClientesRepository;
import br.com.gvp.vendas.repository.ItensPedidoRepository;
import br.com.gvp.vendas.repository.PedidosRepository;
import br.com.gvp.vendas.repository.ProdutosRepository;
import br.com.gvp.vendas.service.PedidoService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService{

	private final PedidosRepository pedidosRepository;
	private final ClientesRepository clientesRepository;
	private final ProdutosRepository produtosRepository;
	private final ItensPedidoRepository itensPedidoRepository;

	@Override
	@Transactional
	public Pedido salvar(PedidoDTO dto) {
		Integer idCliente = dto.getCliente();
		Cliente cliente = clientesRepository
			.findById(idCliente)
			.orElseThrow(() -> new RegraNegocioException("Código de cliente inválido!"));
		
		Pedido pedido = new Pedido();
		pedido.setTotal(dto.getTotal());
		pedido.setData(LocalDate.now());
		pedido.setCliente(cliente);
		pedido.setStatus(StatusPedido.REALIZADO);
		
		List<ItemPedido> itensPedido = converterItens(pedido, dto.getItens());
		pedidosRepository.save(pedido);
		itensPedidoRepository.saveAll(itensPedido);
		pedido.setItens(itensPedido);

		return pedido;
	}
	
	private List<ItemPedido> converterItens(Pedido pedido, List<ItemPedidoDTO> itens) {
		if(itens.isEmpty()) {
			throw new RegraNegocioException("Não é realizar um pedido sem itens");
		}
		
		return itens
				.stream()
				.map(dto -> {
					Integer idProduto = dto.getProduto();
					Produto produto = produtosRepository
						.findById(idProduto)
						.orElseThrow(() -> new RegraNegocioException("Código de produto inválido! " + idProduto));
					
					ItemPedido itemPedido = new ItemPedido();
					itemPedido.setQuantidade(dto.getQuantidade());
					itemPedido.setPedido(pedido);
					itemPedido.setProduto(produto);
					return itemPedido;
				}).collect(Collectors.toList());
	}

	@Override
	public Optional<Pedido> obterPedidoCompleto(Integer id) {
		return pedidosRepository.findByIdFetchItens(id);
	}

	@Override
	@Transactional
	public void atualizaStatus(Integer id, StatusPedido statusPedido) {
		pedidosRepository
			.findById(id)
			.map(pedido -> {
				pedido.setStatus(statusPedido);
				return pedidosRepository.save(pedido);
			}).orElseThrow(() -> new PedidoNaoEncontradoException() );
		
	}
}
