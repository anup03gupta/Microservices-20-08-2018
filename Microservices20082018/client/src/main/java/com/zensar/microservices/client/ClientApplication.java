package com.zensar.microservices.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class ClientApplication {
	
	/*@Autowired
	private EurekaClient client;*/
	
	@Autowired
	private DiscoveryClient client;
	
	@Autowired
	private RestTemplateBuilder restTemplateBuilder;

	public static void main(String[] args) {
		SpringApplication.run(ClientApplication.class, args);
	}
	
	@GetMapping
	public String getService() {
		
		//RestTemplate template123=new RestTemplate();
		
		RestTemplate template=restTemplateBuilder.build();
		
		//InstanceInfo instanceInfo = client.getNextServerFromEureka("service", false);
		
		List<ServiceInstance> instances = client.getInstances("service");
		
		String homePageUrl=instances.get(0).getUri().toString();
		
		//String homePageUrl = instanceInfo.getHomePageUrl();
		
		ResponseEntity<String> response = template.exchange(homePageUrl, HttpMethod.GET, null, String.class);
		
		return response.getBody();
	}
}
