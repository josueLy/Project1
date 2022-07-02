package project1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@ComponentScan("project1.controller")
@EnableMongoRepositories("project1.repository")
@ComponentScan("project1.service")
public class PrjDemoBcApplication {

	public static void main(String[] args) {
		SpringApplication.run(PrjDemoBcApplication.class, args);
	}

}
