package com.example.demo;



import java.util.HashMap;
import java.util.Map;

import javax.xml.ws.Endpoint;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor;
import org.apache.wss4j.common.ConfigurationConstants;
import org.apache.wss4j.dom.WSConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigWs {

	@Autowired
	private Bus bus;
	
	@Bean
	public Endpoint endPoint1() {
		EndpointImpl ePImpl= new EndpointImpl(bus, new Calculator());
		ePImpl.publish("/cal");
		return ePImpl;

	}
	
	@Bean
	public Endpoint endPoint2() {
		EndpointImpl ePImpl= new EndpointImpl(bus, new CustomerOrderImpl());
		ePImpl.publish("/customerorderservice");
		return ePImpl;

	}
	
	@Bean
	public Endpoint endPoint3() {
		EndpointImpl ePImpl= new EndpointImpl(bus, new CustomerOrderImpl());
		ePImpl.publish("/secureorder");
		Map<String, Object> inProps = new HashMap<>();
		inProps.put(ConfigurationConstants.ACTION, ConfigurationConstants.USERNAME_TOKEN);
		inProps.put(ConfigurationConstants.PASSWORD_TYPE, WSConstants.PW_TEXT);
		inProps.put(ConfigurationConstants.PW_CALLBACK_CLASS, UTPasswordCallback.class.getName());
		
		WSS4JInInterceptor wssIn = new WSS4JInInterceptor(inProps);
		ePImpl.getInInterceptors().add(wssIn);
		
		return ePImpl;

	}
}