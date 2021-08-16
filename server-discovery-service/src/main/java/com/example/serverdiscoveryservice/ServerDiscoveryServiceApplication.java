package com.example.serverdiscoveryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class ServerDiscoveryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerDiscoveryServiceApplication.class, args);
	}

}
