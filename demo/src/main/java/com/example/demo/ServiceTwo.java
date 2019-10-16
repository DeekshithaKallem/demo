package com.example.demo;

import java.util.Calendar;
import java.util.PrimitiveIterator;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@EnableAutoConfiguration
@RestController
public class ServiceTwo {
	@Value("${servicethree.url:http://localhost:9095}")
	private String servicethreeUrl;
	
	RestTemplate restTemplate;
	private final PrimitiveIterator.OfLong random = new Random(System.currentTimeMillis())
			.longs(1000, 1500)
			.iterator();

	@GetMapping("/api/serviceTwo")
	public String sayItsServiceTwo() throws InterruptedException {
		long currentTime = (System.currentTimeMillis() / 1000);
		long modulo = currentTime % 900;

		if( modulo >= 0 && modulo < 60) {
			// mocking some random slowdown and failures every 15th minute
			Thread.sleep(random.nextLong());
			throw new RandomException("Anomalous Failure");
		} else if(currentTime % 60 == 1) {
			// and some trickle failure every minute
			throw new RandomException("Random Failure");
		}

		return "Hello From Service Two, It's " + Calendar.getInstance().getTime().toString();
	}

	@GetMapping("/api/callServiceThree")
	public String callServiceThree() throws InterruptedException {
		restTemplate = new RestTemplate();
		long currentTime = (System.currentTimeMillis() / 1000);
		long modulo = currentTime % 900;

		if( modulo >= 0 && modulo < 60) {
			// mocking some random slowdown and failures every 15th minute
			Thread.sleep(random.nextLong());
			throw new RandomException("Anomalous Failure");
		} else if(currentTime % 60 == 1) {
			// and some trickle failure every minute
			throw new RandomException("Random Failure");
		}		
		String result = restTemplate.getForObject(servicethreeUrl + "/api/serviceThree", String.class);
		return result;
	}

	private final class RandomException extends RuntimeException {
		RandomException(String message) {
			super(message);
		}
	}			

}
