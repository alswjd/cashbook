package com.gdu.cashbook;

import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@SpringBootApplication
//@SpringBootApplication = @Configration + @Enable + @ComponetScan
public class CashbookApplication {

	public static void main(String[] args) {
		SpringApplication.run(CashbookApplication.class, args);
	}
	
	
	@Bean
	public JavaMailSender getJavaMailSender() {
		//bean에 사용될 설정 세팅
		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl(); 
		
		javaMailSender.setHost("smtp.gmail.com"); //메일 서버 이름
		javaMailSender.setPort(587);
		javaMailSender.setUsername("alswjdd09@gmail.com");
		javaMailSender.setPassword("");
		
		//Properties 세팅
		Properties prop = new Properties(); //Properties == HashMap<String, String>
		prop.setProperty("mail.smtp.auth", "true"); //Properties 타입 String, String
		prop.setProperty("mail.smtp.starttls.enable", "true");
		javaMailSender.setJavaMailProperties(prop);
		
		return javaMailSender;
	}
}
