package br.com.cotiinformatica.domain.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cotiinformatica.components.RabbitMQProducerComponent;
import br.com.cotiinformatica.domain.dtos.ClienteMessage;
import br.com.cotiinformatica.domain.dtos.ClienteRequest;
import br.com.cotiinformatica.domain.dtos.ClienteResponse;
import br.com.cotiinformatica.domain.dtos.CriarClienteRequest;
import br.com.cotiinformatica.domain.dtos.EnderecoResponse;
import br.com.cotiinformatica.domain.entities.Cliente;
import br.com.cotiinformatica.domain.entities.Endereco;
import br.com.cotiinformatica.domain.exceptions.ClienteNotFoundException;
import br.com.cotiinformatica.domain.exceptions.EnderecoNotFoundException;
import br.com.cotiinformatica.domain.interfaces.ClienteService;
import br.com.cotiinformatica.repositories.ClienteRepository;
import br.com.cotiinformatica.repositories.EnderecoRepository;

@Service
public class ClienteServiceImpl implements ClienteService {

	@Autowired ClienteRepository clienteRepository;
	@Autowired EnderecoRepository enderecoRepository;
	@Autowired RabbitMQProducerComponent MQProducerComponent;
	
	@Override
	public ClienteResponse cadastrarCliente(CriarClienteRequest request) {
		
		var cliente = new Cliente();
		cliente.setNome(request.getNome());
		cliente.setCpf(request.getCpf());
		cliente.setEmail(request.getEmail());
		cliente.setDataNascimento(request.getDataNascimento());
		var endereco = new Endereco();
		endereco.setLogradouro(request.getEndereco().getLogradouro());
		endereco.setBairro(request.getEndereco().getBairro());
		endereco.setCep(request.getEndereco().getCep());
		endereco.setCidade(request.getEndereco().getCidade());
		endereco.setComplemento(request.getEndereco().getComplemento());
		endereco.setNumero(request.getEndereco().getNumero());
		endereco.setUf(request.getEndereco().getUf());
		endereco.setCliente(cliente);
		
		cliente.setEnderecos(List.of(endereco));
		
		clienteRepository.save(cliente);
		
		var clienteMessage = new ClienteMessage();
		clienteMessage.setNome(cliente.getNome());
		clienteMessage.setEmail(cliente.getEmail());
		clienteMessage.setDataHoraCriacao(LocalDateTime.now());
		
		MQProducerComponent.send(clienteMessage);
		
		return copyToResponse(cliente);		
	}

	@Override
	public ClienteResponse editarCliente(UUID id,ClienteRequest request) {
		
		var cliente = clienteRepository.findById(id).orElseThrow(() -> new ClienteNotFoundException());
		
		cliente.setNome(request.getNome());
		cliente.setCpf(request.getCpf());
		cliente.setEmail(request.getEmail());
		cliente.setDataNascimento(request.getDataNascimento());;
		
		var endereco = enderecoRepository.findByIdAndClienteId(request.getEndereco().getId(), cliente.getId())
				.orElseThrow(() -> new EnderecoNotFoundException());
		endereco.setLogradouro(request.getEndereco().getLogradouro());
		endereco.setBairro(request.getEndereco().getBairro());
		endereco.setCep(request.getEndereco().getCep());
		endereco.setCidade(request.getEndereco().getCidade());
		endereco.setComplemento(request.getEndereco().getComplemento());
		endereco.setNumero(request.getEndereco().getNumero());
		endereco.setUf(request.getEndereco().getUf());
		endereco.setCliente(cliente);
		
		enderecoRepository.save(endereco);
		
		cliente.setEnderecos(new ArrayList<>(List.of(endereco)));
		
		clienteRepository.save(cliente);
		
		return copyToResponse(cliente);
	}

	@Override
	public ClienteResponse deletarCliente(UUID id) {
		var cliente = clienteRepository.findById(id).orElseThrow(() -> new ClienteNotFoundException());
		
		clienteRepository.delete(cliente);
		
		return copyToResponse(cliente);
	}

	@Override
	public List<ClienteResponse> listarClientes() {
		var clientes = clienteRepository.findAll();
		
		return clientes.stream()
				.map(this::copyToResponse)
				.collect(Collectors.toList());
	}

	@Override
	public ClienteResponse buscarPorId(UUID id) {
		var cliente = clienteRepository.findById(id).orElseThrow(() -> new ClienteNotFoundException());
		
		return copyToResponse(cliente);
	}


	private ClienteResponse copyToResponse(Cliente c) {	
		
		var resp = new ClienteResponse();
		resp.setId(c.getId());
		resp.setNome(c.getNome());
		resp.setCpf(c.getCpf());
		resp.setEmail(c.getEmail());
		resp.setDataNascimento(c.getDataNascimento());
		
		var e = c.getEnderecos().get(0);
		var endereco = new EnderecoResponse();
		
		endereco.setId(e.getId());
		endereco.setBairro(e.getBairro());
		endereco.setCep(e.getCep());
		endereco.setCidade(e.getCidade());
		endereco.setComplemento(e.getComplemento());
		endereco.setLogradouro(e.getLogradouro());
		endereco.setNumero(e.getNumero());
		endereco.setUf(e.getUf());
		resp.setEnderecos(List.of(endereco));
		
		return resp;
	}

}
