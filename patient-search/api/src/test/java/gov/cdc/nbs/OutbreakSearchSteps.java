package gov.cdc.nbs;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import gov.cdc.nbs.controller.CodeValueGeneralController;
import gov.cdc.nbs.entity.srte.CodeValueGeneral;
import gov.cdc.nbs.graphql.GraphQLPage;
import gov.cdc.nbs.repository.CodeValueGeneralRepository;
import gov.cdc.nbs.support.OutbreakMother;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
@Rollback(false)
public class OutbreakSearchSteps {

    @Autowired
    private CodeValueGeneralRepository codeValueGeneralRepository;
    @Autowired
    private CodeValueGeneralController codeValueGeneralController;

    private Page<CodeValueGeneral> response;

    @Given("Outbreaks exist")
    public void outbreaks_exist() {
        var outbreak = OutbreakMother.testOutbreak();
        if (!codeValueGeneralRepository.existsById(outbreak.getId())) {
            codeValueGeneralRepository.save(outbreak);
        }
    }

    @When("I search for outbreaks")
    public void i_search_for_outbreaks() {
        response = codeValueGeneralController.findAllOutbreaks(new GraphQLPage(10, 0));
    }

    @Then("I find outbreaks")
    public void i_find_outbreaks() {
        assertTrue(response.getTotalElements() > 0);
    }

}
