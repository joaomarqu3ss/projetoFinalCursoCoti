package br.com.cotiinformatica.handlers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import br.com.cotiinformatica.domain.exceptions.ClienteNotFoundException;
import br.com.cotiinformatica.domain.exceptions.EnderecoNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ClienteNotFoundException.class)
	public ResponseEntity<Map<String, Object>> handleClienteNotFoundException(ClienteNotFoundException ex, WebRequest request){
		var body = new HashMap<String,Object>();
		
		body.put("status", HttpStatus.NOT_FOUND);
		body.put("erro", ex.getMessage());
		
		return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request){
		var body = new HashMap<String,Object>();
		
		body.put("status", HttpStatus.BAD_REQUEST);
		body.put("erro", ex.getMessage());
		
		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(EnderecoNotFoundException.class)
	public ResponseEntity<Map<String, Object>> handleEnderecoNotFoundException(EnderecoNotFoundException ex, WebRequest request){
		var body = new HashMap<String,Object>();
		
		body.put("status", HttpStatus.NOT_FOUND);
		body.put("erro", ex.getMessage());
		
		return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
		
	}
	
}
