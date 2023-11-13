package gov.cdc.nbs.questionbank;

import gov.cdc.nbs.authentication.EnableNBSAuthentication;
import gov.cdc.nbs.id.EnableNBSIdGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;

@SpringBootApplication
@EnableNBSAuthentication
@EnableNBSIdGenerator
@EnableOpenApi
public class QuestionBankApplication {

  public static void main(String[] args) {
    SpringApplication.run(QuestionBankApplication.class, args);
  }

}
