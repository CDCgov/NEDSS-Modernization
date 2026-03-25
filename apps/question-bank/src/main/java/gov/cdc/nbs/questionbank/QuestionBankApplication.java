package gov.cdc.nbs.questionbank;

import gov.cdc.nbs.authentication.EnableNBSAuthentication;
import gov.cdc.nbs.id.EnableNBSIdGenerator;
import gov.cdc.nbs.redirect.EnableRedirection;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableNBSAuthentication
@EnableNBSIdGenerator
@EnableRedirection
public class QuestionBankApplication {

  public static void main(String[] args) {
    SpringApplication.run(QuestionBankApplication.class, args);
  }
}
