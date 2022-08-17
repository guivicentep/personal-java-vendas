package br.com.gvp.vendas.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Cliente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length = 100)
	@NotEmpty(message = "Campo nome é obrigatório!")
	private String nome;
	
	@Column(length = 11)
	@NotEmpty(message = "Campo CPF é obrigatório!")
	@CPF(message = "Informe um CPF válido!")
	private String cpf;
	
	@JsonIgnore
	@OneToMany(mappedBy = "cliente")
	private Set<Pedido> pedidos;

	public Cliente(Integer id, String nome) {
		this.id = id;
		this.nome = nome;
	}
		
}
