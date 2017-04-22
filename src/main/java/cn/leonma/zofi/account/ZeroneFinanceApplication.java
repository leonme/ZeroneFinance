package cn.leonma.zofi.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class ZeroneFinanceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZeroneFinanceApplication.class, args);
	}
}
