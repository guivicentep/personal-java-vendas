package br.com.gvp.vendas.dto;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.NotNull;

import br.com.gvp.vendas.validation.NotEmptyList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PedidoDTO {
	@NotNull(message = "Informe o código do cliente")
	private Integer cliente;
	
	@NotNull(message = "O campo total do pedido é obrigatório!")
	private BigDecimal total;
	
	@NotEmptyList(message = "Pedido não pode ser realizado sem itens!")
	private List<ItemPedidoDTO> itens;
}
