package br.com.cotiinformatica.domain.entities;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "endereco")
@Data
public class Endereco {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@Column(nullable = false)
	private String logradouro;
	
	@Column(nullable = false)
	private String complemento;
	
	@Column(nullable = false)
	private String numero; 
	
	@Column(nullable = false)
	private String bairro;
	
	@Column(nullable = false)
	private String cidade; 
	
	@Column(nullable = false)
	private String uf;
	
	@Column(nullable = false)
	private String cep;
	
	@ManyToOne
	@JoinColumn(name = "cliente_id", nullable = false)
	private Cliente cliente;
}
