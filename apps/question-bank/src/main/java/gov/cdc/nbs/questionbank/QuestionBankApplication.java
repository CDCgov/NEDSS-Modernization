package gov.cdc.nbs.questionbank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import gov.cdc.nbs.authentication.EnableNBSAuthentication;
import gov.cdc.nbs.id.EnableNBSIdGenerator;
import gov.cdc.nbs.redirect.EnableRedirection;
import springfox.documentation.oas.annotations.EnableOpenApi;

@SpringBootApplication
@EnableNBSAuthentication
@EnableNBSIdGenerator
@EnableOpenApi
@EnableRedirection
public class QuestionBankApplication {

  public static void main(String[] args) {
    SpringApplication.run(QuestionBankApplication.class, args);
  }

}
