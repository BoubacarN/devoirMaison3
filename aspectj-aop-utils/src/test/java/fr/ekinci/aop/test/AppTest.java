package fr.ekinci.aop.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author Gokan EKINCI
 */
@SpringBootApplication
@EnableAspectJAutoProxy
public class AppTest {

	public static void main(String[] args) {
		SpringApplication.run(AppTest.class, args);
	}

}
