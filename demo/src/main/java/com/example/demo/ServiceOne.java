package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@EnableAutoConfiguration
@RestController
public class ServiceOne {
	
	@Value("${servicetwo.url:http://localhost:9094}")
	private String servicetwoUrl;

	@Autowired
	private RestTemplate restTemplate;

	@RequestMapping("/serviceOne")
	public String callServiceTwo()  {
		return restTemplate.getForObject(servicetwoUrl + "/api/serviceTwo", String.class);
	}

	@RequestMapping("/serviceThree")
	public String callServiceThreeUsingServiceTwo() {		
		return restTemplate.getForObject(servicetwoUrl + "/api/callServiceThree", String.class);
	}
	

	@Bean
	RestTemplate restTemplate() {		
		return new RestTemplate();
	}
}

