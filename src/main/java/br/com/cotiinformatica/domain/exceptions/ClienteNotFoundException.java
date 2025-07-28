package br.com.cotiinformatica.domain.exceptions;

@SuppressWarnings("serial")
public class ClienteNotFoundException extends RuntimeException {
	
	@Override
	public String getMessage() {
		
		return "Cliente não encontrado. Por favor verifique o ID informado.";
	}
}
