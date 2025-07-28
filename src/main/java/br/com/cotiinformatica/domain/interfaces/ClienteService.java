package br.com.cotiinformatica.domain.interfaces;

import java.util.List;
import java.util.UUID;

import br.com.cotiinformatica.domain.dtos.ClienteRequest;
import br.com.cotiinformatica.domain.dtos.ClienteResponse;
import br.com.cotiinformatica.domain.dtos.CriarClienteRequest;


public interface ClienteService {
		
	ClienteResponse cadastrarCliente(CriarClienteRequest request);
	
	ClienteResponse editarCliente(UUID id, ClienteRequest request);
	
	ClienteResponse deletarCliente(UUID id);
	
	List<ClienteResponse> listarClientes();
	
	ClienteResponse buscarPorId(UUID id);
	
}
