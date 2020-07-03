package hello;
import org.springframework.stereotype.Component;
import org.springframework.jms.annotation.JmsListener;

@Component
public class Receiver {
	
//Annotation that marks a method to be the target of a JMS message listener 
    //on the specified destination. The containerFactory identifies the org.springframework.jms.config.JmsListenerContainerFactory to use to build the JMS listener container. 
	//If not set, a default container factory is assumed to be available with a bean name of jmsListenerContainerFactory 
	//unless an explicit default has been provided through configuration.

//Spring’s JmsTemplate can receive messages directly through its receive method, but that only works synchronously, 
//meaning it will block. Use a listener container such as DefaultMessageListenerContainer with a 
//cache-based connection factory, so you can consume messages asynchronously and with maximum connection efficiency.	
	@JmsListener(destination= "jmsmailbox", containerFactory="mailjmsfactory")
	public void receiveMsg(Email email) {
		System.out.println("Received message from Jms <" + email + ">");
	}
}