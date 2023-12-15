package com.roberthj.musicmaster;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}

//TODO:
// * Pass in an artist
// * Get a list of concerts from Ticketmaster
// * add endpoint where you can save a concert you want to go to
// * Get a reminder when the concert is approaching
// * Create gRPC for all endpoints as well
