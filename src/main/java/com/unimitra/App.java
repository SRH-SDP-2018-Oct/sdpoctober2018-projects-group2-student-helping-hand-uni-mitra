package com.unimitra;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import lombok.experimental.PackagePrivate;


@SpringBootApplication
@ComponentScan("com.unimitra")
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	} 
}  

