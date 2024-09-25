package email.tunemail;

// main class that bootstraps the application

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class TunemailApplication {

	public static void main(String[] args) {
		SpringApplication.run(TunemailApplication.class, args);
	}

}
