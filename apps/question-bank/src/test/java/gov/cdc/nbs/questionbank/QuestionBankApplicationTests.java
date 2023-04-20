package gov.cdc.nbs.questionbank;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import gov.cdc.nbs.questionbank.questionnaire.QuestionnaireResolver;

@SpringBootTest
@ActiveProfiles("test")
class QuestionBankApplicationTests {

    @Autowired
    private QuestionnaireResolver resolver;

    @Test
    void contextLoads() {
        QuestionBankApplication.main(new String[] {});
        assertNotNull(resolver);
    }

}
