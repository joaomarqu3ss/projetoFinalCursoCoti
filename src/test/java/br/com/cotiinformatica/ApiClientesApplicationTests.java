package br.com.cotiinformatica;

import static org.assertj.core.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;

import br.com.cotiinformatica.domain.dtos.ClienteRequest;
import br.com.cotiinformatica.domain.dtos.ClienteResponse;
import br.com.cotiinformatica.domain.dtos.CriarClienteRequest;
import br.com.cotiinformatica.domain.dtos.CriarEnderecoRequest;
import br.com.cotiinformatica.domain.dtos.EnderecoRequest;

@AutoConfigureMockMvc
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ApiClientesApplicationTests {

	@Autowired MockMvc mockMvc;
	@Autowired ObjectMapper mapper;
	
	private static UUID idCliente;
	private static UUID idEndereco;
	
	
	@Order(1)
	@Test
	@DisplayName("cadastro")
	void cadastroComSucesso() {
		
		try {
			
			var faker = new Faker();
			var request = new CriarClienteRequest();
			request.setEndereco(new CriarEnderecoRequest());
			
			var dataFake = LocalDate.of(2007, 7, 27);
			
			request.setNome(faker.name().fullName());
			request.setEmail(faker.internet().emailAddress());
			request.setCpf(faker.regexify("^\\d{11}$|^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$"));
			request.setDataNascimento(dataFake);
			request.getEndereco().setBairro("Carioca");
			request.getEndereco().setCep(faker.regexify("^\\d{5}-\\d{3}$"));
			request.getEndereco().setCidade("Rio de Janeiro");
			request.getEndereco().setComplemento("Casa");
			request.getEndereco().setLogradouro("Rua");
			request.getEndereco().setNumero(faker.number().toString());
			request.getEndereco().setUf("RJ");
			
			var result = mockMvc.perform(post("/api/clientes")
					.contentType("application/json")
					.content(mapper.writeValueAsString(request)))
					.andExpect(status().isOk())
					.andReturn();
			
			var content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);			

			var response = mapper.readValue(content, ClienteResponse.class);
			
			
			idCliente = response.getId();
			idEndereco = response.getEnderecos().get(0).getId();
			
			
		} catch(Exception e) {
			fail("Teste falhou " + e.getMessage());
		}
	
	}
	
	@Order(2)
	@Test
	@DisplayName("edicao")
	void atualizarComSucesso() {
	
		try {
		var faker = new Faker();
		var request = new ClienteRequest();
		request.setEndereco(new EnderecoRequest());
		
		var dataFake = LocalDate.of(2007, 7, 27);
		
		request.setId(idCliente);
		request.setNome(faker.name().fullName());
		request.setEmail(faker.internet().emailAddress());
		request.setCpf(faker.regexify("^\\d{11}$|^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$"));
		request.setDataNascimento(dataFake);
		request.getEndereco().setId(idEndereco);
		request.getEndereco().setBairro("Carioca");
		request.getEndereco().setCep(faker.regexify("^\\\\d{5}-\\\\d{3}$|^\\\\d{8}$"));
		request.getEndereco().setCidade("Rio de Janeiro");
		request.getEndereco().setComplemento("Apt");
		request.getEndereco().setLogradouro("Avenida");
		request.getEndereco().setNumero(faker.number().toString());
		request.getEndereco().setUf("RJ");
		
		} catch (Exception e) {
			fail("Teste falhou " + e.getMessage());
		}
	}
	
	@Order(3)
	@Test
	@DisplayName("Listar")
	void listarComSucesso() {
		try {
		mockMvc.perform(get("/api/clientes")
				.contentType("application/json"))
				.andExpect(status().isOk())
				.andReturn();
		} catch (Exception e) {
			fail("Teste falhou " + e.getMessage());
		}
	}
	
	@Order(4)
	@Test
	@DisplayName("Listar por ID")
	void buscarPorId() {
		try {
			
			mockMvc.perform(get("/api/clientes/{id}", idCliente))
			.andExpect(status().isOk())
			.andReturn();
			
		} catch (Exception e) {
			fail("Teste falhou: " + e.getMessage());
		}
	}
	
	@Order(5)
	@Test
	@DisplayName("deletar")
	void deletarComSucesso() {
		
		try {
		
			mockMvc.perform(delete("/api/clientes/{id}", idCliente))
				.andExpect(status().isOk())
				.andReturn();
		
		} catch (Exception e) {
			fail("Teste falhou " + e.getMessage());
		}
	
		}
}
