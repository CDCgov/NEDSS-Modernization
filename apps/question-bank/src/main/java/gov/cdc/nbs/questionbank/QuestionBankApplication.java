package gov.cdc.nbs.questionbank;

import gov.cdc.nbs.authentication.EnableNBSAuthentication;
import gov.cdc.nbs.id.EnableNBSIdGenerator;
import gov.cdc.nbs.redirect.outgoing.EnableClassicInteraction;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;

@SpringBootApplication
@EnableNBSAuthentication
@EnableNBSIdGenerator
@EnableOpenApi
@EnableClassicInteraction
public class QuestionBankApplication {

  public static void main(String[] args) {
    SpringApplication.run(QuestionBankApplication.class, args);
  }

}
