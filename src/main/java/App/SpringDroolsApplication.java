package App;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class SpringDroolsApplication {

	public static void main(String[] args) {

		SpringApplication.run(SpringDroolsApplication.class, args);
	}

}
