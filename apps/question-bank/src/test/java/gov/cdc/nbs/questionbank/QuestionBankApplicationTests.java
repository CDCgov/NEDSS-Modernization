package gov.cdc.nbs.questionbank;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import gov.cdc.nbs.questionbank.repository.QuestionnaireRepository;

@SpringBootTest
@ActiveProfiles("test")
class QuestionBankApplicationTests {

    @Autowired
    private QuestionnaireRepository repository;

    @Test
    void contextLoads() {
        assertNotNull(repository);
        assertEquals(0, repository.count());
    }

}
