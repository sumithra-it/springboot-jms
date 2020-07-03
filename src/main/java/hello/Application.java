package hello;

import javax.jms.ConnectionFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

//https://spring.io/guides/gs/messaging-jms/
//https://www.baeldung.com/spring-jms
//@EnableJms creates message listener container for @JmsListener methods 

@SpringBootApplication
@EnableJms
public class Application {

	//Bean mailjmsfactory is used by JmsListener
	@Bean
	public JmsListenerContainerFactory<?> mailjmsfactory(ConnectionFactory connectionFactory,
			DefaultJmsListenerContainerFactoryConfigurer configurer) {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		// This provides all boot's default to this factory, including the message converter
		configurer.configure(factory, connectionFactory);
		return factory;
	}
	
	@Bean  // Serialize message content to json using TextMessage
	public MessageConverter jacksonJmsMessageConverter() {
		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
	    converter.setTargetType(MessageType.TEXT);
	    converter.setTypeIdPropertyName("_type");
	    return converter;
	}
	//Two beans JmsTemplate and ConnectionFactory are created automatically by Spring Boot.
	//By default, Spring Boot creates a JmsTemplate configured to transmit to queues by having pubSubDomain set to false. 
	//The JmsMessageListenerContainer is also configured the same. To override, set spring.jms.isPubSubDomain=true via Boot’s property settings (either inside application.properties or by environment variable). 
	//Then make sure the receiving container has the same setting.
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
		
		JmsTemplate jmstemplate = context.getBean(JmsTemplate.class);
		
		// Send a message with a POJO
		System.out.println("Sending an email message.");
		jmstemplate.convertAndSend("jmsmailbox", new Email("sumithra.ramadas@gmail.com", "Hello from JMS application"));
	}
	
}
