package gov.cdc.nbs.questionbank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;



@Import({gov.cdc.nbs.service.UserService.class, gov.cdc.nbs.config.security.JWTSecurityConfig.class})
@EnableJpaRepositories({"gov.cdc.nbs.questionbank", "gov.cdc.nbs.authentication"})
@EntityScan({"gov.cdc.nbs.questionbank", "gov.cdc.nbs"})
@SpringBootApplication
public class QuestionBankApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuestionBankApplication.class, args);
    }

}
