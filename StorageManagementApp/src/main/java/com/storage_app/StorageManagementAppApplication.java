package com.storage_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={"com.storage_app"})
public class StorageManagementAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(StorageManagementAppApplication.class, args);
	}

}
