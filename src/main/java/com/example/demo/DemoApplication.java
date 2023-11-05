package com.example.demo;


import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

    @Bean
    public ApplicationRunner runner(CustomerRepo customerRepository) {
        return args -> {
    	Customer customer = new Customer(
  				  "02920393", "Customer First");
  		customerRepository.save(customer);
  		Customer retrievedCustomer = customerRepository.findById("02920393").get();
  		System.out.println(retrievedCustomer.toString());
        };
    }
}
