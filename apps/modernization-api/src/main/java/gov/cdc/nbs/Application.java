package gov.cdc.nbs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;

@SpringBootApplication
@EnableOpenApi
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
