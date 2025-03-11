package com.img;

import com.img.config.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProperties.class)
public class OAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(OAuthApplication.class, args);
	}

}
