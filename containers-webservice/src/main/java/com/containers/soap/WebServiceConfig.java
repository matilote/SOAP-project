package com.containers.soap;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.ws.Endpoint;


import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class WebServiceConfig {

	@Autowired
	private Bus bus;

	@Bean
	public Endpoint endpoint() throws DatatypeConfigurationException {
		Endpoint endpoint = new EndpointImpl(bus, new DepotRepairServiceImpl());
		endpoint.publish("/depotrepairservice");

		return endpoint;

	}

}
