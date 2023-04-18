package gov.cdc.nbs.questionbank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import lombok.Generated;

@SpringBootApplication
public class QuestionBankApplication {

    @Generated // tells sonar to ignore this method
    public static void main(String[] args) {
        SpringApplication.run(QuestionBankApplication.class, args);
    }

}
