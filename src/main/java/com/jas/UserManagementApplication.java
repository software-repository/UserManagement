package com.jas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
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
