package br.com.gvp.vendas.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CredenciaisDTO {
	
	private String login;
	private String senha;
}
