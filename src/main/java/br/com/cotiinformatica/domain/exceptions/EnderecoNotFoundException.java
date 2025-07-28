package br.com.cotiinformatica.domain.exceptions;

@SuppressWarnings("serial")
public class EnderecoNotFoundException extends RuntimeException {

	@Override
	public String getMessage() {
		
		return "Endereco não encontrado. Por favor verifique o ID informado.";
	}
}
