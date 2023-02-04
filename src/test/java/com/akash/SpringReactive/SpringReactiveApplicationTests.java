package com.akash.SpringReactive;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootTest
class SpringReactiveApplicationTests {

	@Test
	void contextLoads() {
	}
	
	@Test
	public void monoTest() {
		//Mono & Flux will acts as Publisher
		Mono<String> monoString = Mono.just("Aakash");
		
//		If Below statement executes then we can see every step of reactiev Programming workflow.
//		Mono<String> monoString = Mono.just("Aakash").log();
		
		// Second step would be to subscribe what has been produced by Publisher
		monoString.subscribe(System.out::println);
	
		
	}
	
	@Test
	public void fluxTest() {
		
//		Flux<String> fluxString = Flux.just("Spring","Spring Boot", "Hibernate" ,"Microservices");
		
//		In order to get Reactive workflow we have used .log() method
		Flux<String> fluxString = Flux.just("Spring","Spring Boot", "Hibernate" ,"Microservices").log();
		
//		fluxString.limitRequest(1) // For limiting the number of request from the Publisher
//				  .subscribe(System.out::println);
		
		fluxString.subscribe(System.out::println);
		
		
		
		
	}
	
	
	
	
	

}
