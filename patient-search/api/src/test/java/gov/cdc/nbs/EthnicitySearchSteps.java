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
import gov.cdc.nbs.support.EthnicityMother;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
@Rollback(false)
public class EthnicitySearchSteps {

    @Autowired
    private CodeValueGeneralRepository codeValueGeneralRepository;
    @Autowired
    private CodeValueGeneralController codeValueGeneralController;

    private Page<CodeValueGeneral> response;

    @Given("Ethnicities exist")
    public void ethnicities_exist() {
        var hispanicOrLatino = EthnicityMother.hispanicOrLatino();
        if (!codeValueGeneralRepository.existsById(hispanicOrLatino.getId())) {
            codeValueGeneralRepository.save(hispanicOrLatino);
        }
        var notHispanicOrLatino = EthnicityMother.notHispanicOrLatino();
        if (!codeValueGeneralRepository.existsById(notHispanicOrLatino.getId())) {
            codeValueGeneralRepository.save(notHispanicOrLatino);
        }
        var unknown = EthnicityMother.unknown();
        if (!codeValueGeneralRepository.existsById(unknown.getId())) {
            codeValueGeneralRepository.save(unknown);
        }
    }

    @When("I search for ethnicities")
    public void i_search_for_ethnicities() {
        response = codeValueGeneralController.findAllEthnicityValues(new GraphQLPage(10, 0));
    }

    @Then("I find ethnicities")
    public void i_find_ethnicities() {
        assertTrue(response.getTotalElements() > 0);
    }

}
