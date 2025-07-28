package br.com.cotiinformatica.domain.dtos;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class ClienteResponse {
	
	private UUID id;
	
	private String nome;
	private String email;
	private String cpf;
	private LocalDate dataNascimento;
	private List<EnderecoResponse> enderecos;
}
