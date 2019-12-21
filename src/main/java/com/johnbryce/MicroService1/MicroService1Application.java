package com.johnbryce.MicroService1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
@EnableEurekaClient
@EnableHystrix
@SpringBootApplication
public class MicroService1Application {

	@Autowired
	RestTemplate restTemplate;

	public static void main(String[] args) {
		SpringApplication.run(MicroService1Application.class, args);
		System.out.println("Micro service 1");
	}

	@Bean
	@LoadBalanced
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	@HystrixCommand(fallbackMethod = "backupData")
	@GetMapping("/")
	public String getData() {
		return String.format("Hello %s", restTemplate.getForObject("http://MICRO2/data", String.class));

	}

	public String backupData() {
		return "Moshe";
	}

}
