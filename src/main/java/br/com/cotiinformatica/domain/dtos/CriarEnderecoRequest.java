package br.com.cotiinformatica.domain.dtos;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CriarEnderecoRequest {

	private String logradouro;
	
	private String complemento;
	
	private String numero;
	
	private String bairro;
	
	private String cidade;
	
	private String uf;
	
	@Pattern(regexp = "^\\d{5}-\\d{3}$")
	private String cep;
}
