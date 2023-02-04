package com.akash.SpringReactive;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ch.qos.logback.core.recovery.ResilientSyslogOutputStream;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@SpringBootApplication
@Slf4j
public class SpringReactiveApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(SpringReactiveApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
//		callmonoFluxTestStream();		
//		log.info("**Demo to understand the life cycle of Reactive Streams ***");
//		log.info("**Demo to create Stream of MONO ***");
//		monoSuccess();
		
//		log.info("**Demo to create Stream of MONO with ERROR/Exception ***");
//		monoError();
		
//		log.info("**Demo to create Stream of FLUX ***");
//		fluxSuccess();

//		log.info("**Demo to create Stream of FLUX with ERROR/Exception***");
//		fluxError();

//		stocksStartingwithA();

//		valuesgreaterThanX(10);
		
//		stockValuesInUpperCase();
//		flatMapDemo();
		
//		multipleSubscribers();
//		mergeStreams();	
//		zipStreamDemo();
	
		
	
	}
	
	
	public void zipStreamDemo() {
		
		Flux<String> shareOne = Flux.just("Mobikwik", "Tata Motors", "Reliance", "RateGain", "Adani Wilmar");
		Mono<String>  shareTwo = Mono.just("Axis Bank");
		
		final Flux<Tuple2<String,String>> zippedFlux = shareOne.zipWith(shareTwo);
		zippedFlux.log().subscribe(
					value -> System.out.println(value)
				);
		
		Flux.zip(shareOne, shareTwo, shareTwo).subscribe(
				(value) -> System.out.println(value)
			);
		  
		Flux<String> names = Flux.just("Patrick", "Joe", "Mark","Tia", "Harry").log();
           names.filter(s -> s.length() > 3).map(s->s.toUpperCase()).log().subscribe(value -> System.out.println(value));
   
	}
	
//	What if we wanted to merge two streams
	public void mergeStreams() {
		
		Flux<String> stocks = Flux.just("Mobikwik", "Adani Wilmar", "Adani Power", "Rategain", "Reliance").log();
		Flux<String> addStock = Flux.just("Axis Bank");
		
		
		Flux.merge(stocks,addStock).log().subscribe(
					value -> System.out.println(value)
				);
		
	
		
	}
	
//	 What if there are more subscribers for the Flux stock stream? 
	public void multipleSubscribers() {
		
		Flux<Integer> stocks = Flux.just(10,20,30,40,50);
		
		stocks.subscribe(
				value -> System.out.println(value)
				);
		
		stocks.subscribe(
					value -> System.out.println(value)
				);
		
	}
	
//	Exploring .flatMap FUNCTION
	public void flatMapDemo() {
		
		
		 List<Integer> stock1 = Arrays.asList(200, 300, 144, 126);
		 List<Integer> stock2 = Arrays.asList(400, 340, 200);
		 List<Integer> stock3 = Arrays.asList(200, 400, 600, 800);
		 List<List<Integer>> stockPriceList = Arrays.asList(stock1, stock2, stock3);
		 
		 List<Integer> flatStockPriceList = stockPriceList.stream()
				 											.flatMap(list -> list.stream()
				 																 .filter(value -> value > 200)
				 																 .map( value -> value * 200))
				 											.collect(Collectors.toList());
		System.out.println(flatStockPriceList);		 											
		
	}
	
	
//	.map() FUNCTION
	public void stockValuesInUpperCase() {
		
		Flux<String> stocks = Flux.just("Mobikwik", "Adani Wilmar", "Adani Power", "Rategain", "Reliance").log();
		
		stocks.map( s -> s.toUpperCase() )
			  .subscribe( value -> System.out.println(value)
		);
	}
	
//	.filter FUNCTION
	public void valuesgreaterThanX(Integer x) {
		
		Flux<Integer> compareValues = Flux.just(10,20,30,40,50,60,70,9,87,19,24,12).log();
		
		compareValues.filter(value -> value > x)
					.subscribe(
					(n) -> System.out.println(n)
				);
		
	}
	
	//stocks starting with 'A' , .filter FUNCTION
	public void stocksStartingwithA() {
		
		Flux<String> stocks = Flux.just("Mobikwik", "Adani Wilmar", "Adani Power", "Rategain", "Reliance").log();
		
		
		
		//Directly  subscribing
//		stocks.subscribe(
//					(value) -> {
//						if(value.startsWith("A") == true) {
//							System.out.println(value);
//						}
//					}
//				);
		
		//FIRST fILTERING and Then SubscribinG
		Flux<String> startA = stocks.filter( value -> value.startsWith("A"));
		startA.subscribe(
				(value)-> System.out.println(value)
				);
		
	}
	
	
	
	

	private void fluxError() {
		Flux<?> fluxError = Flux.just(10,20,30,40,50).concatWith(
					Flux.error( new RuntimeException("Flux Runtime Exception Occured")));
		
		fluxError.subscribe(
					(value)->{
						System.out.println(value);
					}, (error) -> System.out.println(error.getMessage())
				);
	}

	private void fluxSuccess() {
		
		Flux<Integer> fluxElement = Flux.just(10,20,30,40,50);
		fluxElement.subscribe(
					value -> System.out.println(value)
				);
	}

	private void monoError() {
		
		Mono<?> monoElement = Mono.just(100).then(Mono.error(new RuntimeException("Mono Error Occured"))).log();
		
		monoElement.subscribe(
				value ->System.out.println(value),
				error -> System.out.println(error.getMessage())	
				);
	}

	private void monoSuccess() {
		
		Mono<Integer> monoElement = Mono.just(100);
		monoElement.subscribe(value -> {
			System.out.println(value*value);
		});
	}

	private void callmonoFluxTestStream() {
		
		//These are the Reactive Mono Streams
		//Mono.just is s simple way to emit one element
		Mono<Integer> stockValue = Mono.just(154).log();
		stockValue.subscribe(value -> System.out.println(value));
		
		Mono <String> stock = Mono.just("mobikwik");
		
		System.out.println("########################FLUX VALUES###################################");
		
		Flux<Integer> fluxSample = Flux.just(1,2,3,4,5).log();
		
		fluxSample.subscribe( value -> {
			System.out.println(value * value);
		});
		
	}
}
