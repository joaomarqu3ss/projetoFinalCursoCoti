package br.com.cotiinformatica.components;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.cotiinformatica.domain.dtos.ClienteMessage;

@Component
public class RabbitMQConsumerComponent {

	@Autowired MailSenderComponent mailSenderComponent;
	@Autowired ObjectMapper mapper;
	
	@RabbitListener(queues = "boas-vindas")
	public void consume(@Payload String message) throws Exception {
			
		var cliente = mapper.readValue(message, ClienteMessage.class);
		
			var to = cliente.getEmail();
			var subject = "Parabéns " + cliente.getNome() + ", sua conta foi criada com sucesso";
			var body = WelcomeMessage(cliente);
			
			
			mailSenderComponent.send(to, subject, body, true);
			
		
	}
	
	public String WelcomeMessage(ClienteMessage cliente) {
		return """
		        <!DOCTYPE html>
		        <html>
		        <head>
		            <meta charset="UTF-8" />
		            <title>Bem-vindo(a)!</title>
		        </head>
		        <body style="font-family: Arial, sans-serif; background-color: #f7f7f7; padding: 20px;">
		            <table align="center" width="600" cellpadding="0" cellspacing="0" style="background-color: #ffffff; padding: 20px; border-radius: 8px; box-shadow: 0 0 10px rgba(0,0,0,0.1);">
		                <tr>
		                    <td style="text-align: center;">
		                        <h1 style="color: #0A6EBD;">Seja bem-vindo(a), %s!</h1>
		                        <p style="font-size: 16px; color: #333333;">Estamos muito felizes em ter você conosco.</p>
		                        <p style="font-size: 16px; color: #333333;">Sua conta foi criada com sucesso e agora você já pode começar a utilizar todos os nossos serviços.</p>
		                        <p style="margin-top: 24px;">Qualquer dúvida, estamos à disposição para te ajudar.</p>		                       		                        
		                    </td>
		                </tr>
		            </table>
		        </body>
		        </html>
		        """.formatted(cliente.getNome());
	}
	
}
