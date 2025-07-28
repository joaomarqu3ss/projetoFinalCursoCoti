package br.com.cotiinformatica.domain.dtos;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ClienteMessage {
	
	private String nome;
	private String email;
	private LocalDateTime dataHoraCriacao;
}
