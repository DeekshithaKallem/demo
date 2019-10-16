package com.example.demo;

import java.util.Calendar;
import java.util.PrimitiveIterator;
import java.util.Random;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableAutoConfiguration
@RestController
public class ServiceThree {
	
	private final PrimitiveIterator.OfLong random = new Random(System.currentTimeMillis())
			.longs(1000, 1500)
			.iterator();
	
	private final class RandomException extends RuntimeException {
		RandomException(String message) {
			super(message);
		}
	}
		
	
	@GetMapping("/api/serviceThree")
	public String sayItsServiceThree() throws InterruptedException {	
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
		return "Hello From Service Three, It's " + Calendar.getInstance().getTime().toString();
	}		

}
