package com.msoncloud.mstorage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class MSOnCloudApplication {

	public static void main(String[] args) {
		SpringApplication.run(MSOnCloudApplication.class, args);
	}
}
