package preCapstone.fuseable;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class FuseableApplication {

	public static void main(String[] args) {
		SpringApplication.run(FuseableApplication.class, args);
	}

}

