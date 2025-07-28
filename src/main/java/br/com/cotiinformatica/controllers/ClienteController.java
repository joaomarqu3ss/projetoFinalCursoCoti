package br.com.cotiinformatica.controllers;


import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cotiinformatica.domain.dtos.ClienteRequest;
import br.com.cotiinformatica.domain.dtos.ClienteResponse;
import br.com.cotiinformatica.domain.dtos.CriarClienteRequest;
import br.com.cotiinformatica.domain.interfaces.ClienteService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
	
	@Autowired ClienteService service;
	
	@PostMapping
	public ResponseEntity<ClienteResponse> cadastro(@RequestBody @Valid CriarClienteRequest request){
		return ResponseEntity.ok(service.cadastrarCliente(request));
	}
	@PutMapping
	public ResponseEntity<ClienteResponse> atualizar(@RequestBody @Valid ClienteRequest request){
		
		return ResponseEntity.ok(service.editarCliente(request.getId(),request));
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<ClienteResponse> deletar(@PathVariable UUID id){
		return ResponseEntity.ok(service.deletarCliente(id));
	}
	
	@GetMapping
	public ResponseEntity<List<ClienteResponse>> listar(){
		return ResponseEntity.ok(service.listarClientes());
	}
	
	@GetMapping("{id}") 
	public ResponseEntity<ClienteResponse> buscarPorId(@PathVariable UUID id){
		return ResponseEntity.ok(service.buscarPorId(id));
	}
}
