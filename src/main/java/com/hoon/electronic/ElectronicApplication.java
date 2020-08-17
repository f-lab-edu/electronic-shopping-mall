package com.hoon.electronic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableCaching
@EnableAspectJAutoProxy
@SpringBootApplication
public class ElectronicApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElectronicApplication.class, args);
	}

}
