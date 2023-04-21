package com.jas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableCaching
@EnableScheduling
public class UserManagementApplication {

	public static void main(String[] args) {
		try {
			SpringApplication.run(UserManagementApplication.class, args);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
