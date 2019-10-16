package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@EnableAutoConfiguration
@SpringBootApplication
@RestController
public class DemoApplication {
	
	public static void main(String[] args) {

		String name = args.length > 0 ? args[0].toLowerCase() : "";
		Class application;
		int port;

		if (name.equalsIgnoreCase("serviceone")) {
			application = ServiceOne.class;
			port = 9093;
		}
		else if(name.equals("servicetwo")){
			application = ServiceTwo.class;
			port = 9094;
		} else {
			application = ServiceThree.class;
			port = 9095;
		}

		SpringApplication.run(application,
				"--spring.application.name=" + name,
				"--server.port=" + port
				);        

	}
	
}
