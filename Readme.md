﻿Spring Reactive
In normal Traditionalprogramming/SpringBoot application, There is synchronous and blocking flow. One after the other.
Spring reactive is Asynchronous & non Blocking.
There is publisher and subscriber model, thus more work done in less time.


There is no thread per request model in Reactive Programming.
If in traditional programming, The thread pool is 5, then only 5 concurrent request can be handlesd by DB.
If 6th comes then it has to wait until a connection is made available to that thread. 


Lets see how we would handle it in case of reactive Programming:


Functional Style Code vs rest Code:

	In traditional Programming lets say we want to delete a user then how do we solve this problem isd:


	public void deleteById(Integer customerId){
		userRepository.deleteById(customerId);
	}
	
	In case of reactive Programming , we will tackle this in such a way:
	
	public Mono<User> deletebyId(Integer customerId){
	
		return userRepository.deleteById(customerId)
							 .flatmap(existingUser -> userRepository.delete(existingUser)
							 .then(Mono.just(existingUser));
	}

	//Mono basically means it can handle single object, There is another datatype which is Flux which can handle 'n' number of Objects.
	
	
Data Flow as eventy driven Stream:

	In traditional apporach , 
	
	client ------> Application ----> DB
	
	if new insert/update operation has happened , then client needs to call another apui for that, ikt doesn't have that information.
	
	
	In Reactive what happens is :
	
	If there is a new chnage in DB, then it fires a new Event saying Hey new cvyhnage has occured.
	And any client can subscribe to that.
	In this architecture , DB is a publisher & client is a subscriber.
	
	
	
	If any chnage is happened, DB can publish stream of Data & any client can subscribe to that Data.
	The connection is always in Open state. Thats why we can asyuncchronously subscribe & publish these events.
	
	
3. BackPressure on Data streams:
		
	Get All Items from the Inventory.

	API -------------->   DB
		getAllItems()
		
	What my API does is sends back large volume of Data.
	
	What Scenarios can happen:
	
	1. My applicastiopn wouldn't be able to handle massive amount of Data.
	2. Out of Memory Error can also take place.
	
	
	Send the data in such a way that the API can handle that amount of data returned where we can add a limitation on database driver on how much data we are expecting.
	
	Advantages (Theritical):
	1 Proper CPU Utilisation.
	2 No Downtime.
	3. Asynchronous & non blocking I/O.
	

Reactive programming Specifications: 
	It is nothing but a rule to dewsign a reactive Programming.


4 Interfaces are preesent.These Interfaces have couple of abstract methods)
	
	Publisher	(publish an event(Database Driver))	 Publisher is a datasource which will publish an event.
	
	public interface Publisher<T>{
		public void subscribe(Subscriber<? super T> s);
	}
	Subscriber will have to call this method subscribe in order to register into publisher.
	
	
	Subscriber 	(Subsacribe to an event (say Browser or Backend API)
	
	Subscriber will be the one which will consume/subscribe an event from the Publisher.
	
	public interface Susbscriber<T>{
	
		public void onSubscribe(Subscription S);
		public void onNext(T t);   //Number of data events from Publisher to be subscribed
		public void onError(Throwable t); // if Error occured in one of the event
		public void onComplete(); // If no error occured in any event
	}
	
	Subscription	
	It represents a unique relationship between a subscriber & Publisher
	
	public interface Subscription{
	
		public void request(long n); // Subscriber will call this request method to get data from Publisher 
		public void cancel();
	}



	
	Processor:
	
	
	A processor represents a processing stage which is a publisher and subscriber and must obey  the contracts of both.
	
	public interface Processor<T,R> extends Subscriber<T> , Publisher<R>{
	
	}
	
	
	
Lets put all the interfaces together & Find the workflow in reaactive programming:


1. Subscriber invokes the subscribe() method of Publisher by passing subscriber instance as a input.
2. Publisher will send an event to the subscriber saying that our subscription is successfull.
3. Subscriber will call request(n) method of the Subscription Interface inorder to get data from Publisher. Here n basically means that Subscriber can reuest 'n' number iof data from Publisher.
4. Publisher ill send data stream by invoking onNext(data) method. 
5. If the data streams send by Publisher is completed, then Publisher will invoke onComplete(0 method saying it has sent all the data tsream from its side.
6. If there is any error in sending Data stream , then Publisher ill sire onError() Method.


Also there is a options with the subscriber to ask limited amount of data from the subscriber.

 
	With method request(n)----> we can control the number of data stres that we need from Publisher.[ This is how Backpresure is supported in Reactive Programming]
	
	
How to build Reactive Programming:
	
	Project Reactor { Appropriate library to interact with SpringBoot Application & Recommended as well}
	RxJava
	Java 9 Flow Reactive Stream.
	
	
	
	Project Reactor:
	
	Foruth generation reactive library based on Reactive tsreams specifications used for building non blocking JVM applications.
	
	
	There are two prpject reactor Data types:
	1. Flux
	2. Mono
	
	With thee data types(Flux & Mono), we need to play with Java stream API.
	
	
	---------------------------------------------------------------------------------------------------------------------------------------------------
	
	If we see the packages of these data types(Mono & Flux), they are present in reactor.core.Publisher ,
	Hence these acts as a publisher. If subscriber needs to access these data events then it needs to invoke subscribe method.
	
	
	As soons as we subscribe to an event , publisher starts emitting that event.
	
	If we do Mono.just("Aakash").log() , then every step will be logged like onNext(),onComplete(),onSubscribe(),onError() & so on.
	
	
