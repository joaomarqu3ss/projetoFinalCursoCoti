package br.com.cotiinformatica.domain.dtos;

import java.time.LocalDate;
import java.util.UUID;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ClienteRequest {
	
	private UUID id;
	
	@Size(min = 8, max = 100,message = "Seu nome deve conter de 8 a 100 caracteres")
	@NotBlank(message = "Por favor, informe seu nome.")	
	private String nome;
	
	@Email
	@NotBlank(message = "Por favor, informe seu email.")
	private String email;
	
	@Pattern(regexp = "^\\d{11}$|^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$", message = "Formato de CPF inválido")
	private String cpf;
	
	@NotNull(message = "Por favor, informe sua data de nascimento")
	private LocalDate dataNascimento;
	
	private EnderecoRequest endereco;
}
