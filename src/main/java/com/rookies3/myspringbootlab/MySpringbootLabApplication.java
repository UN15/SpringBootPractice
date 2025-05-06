package com.rookies3.myspringbootlab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MySpringbootLabApplication {

	public static void main(String[] args) {

//		SpringApplication.run(MySpringbootLabApplication.class, args);
		SpringApplication application = new SpringApplication(MySpringbootLabApplication.class);
		//기본적으로 WebApplicationType은 웹 어플리케이션  이다.
		application.setWebApplicationType(WebApplicationType.SERVLET);
		application.run(args);
	}

}
