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
import gov.cdc.nbs.support.IdentificationMother;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
@Rollback(false)
public class IdentificationTypeSearchSteps {

    @Autowired
    private CodeValueGeneralRepository codeValueGeneralRepository;
    @Autowired
    private CodeValueGeneralController codeValueGeneralController;

    private Page<CodeValueGeneral> response;

    @Given("Identification types exist")
    public void identification_types_exist() {
        var driversLicense = IdentificationMother.driversLicense();
        if (!codeValueGeneralRepository.existsById(driversLicense.getId())) {
            codeValueGeneralRepository.save(driversLicense);
        }
        var socialSecurity = IdentificationMother.socialSecurity();
        if (!codeValueGeneralRepository.existsById(socialSecurity.getId())) {
            codeValueGeneralRepository.save(socialSecurity);
        }
    }

    @When("I search for identification types")
    public void i_search_for_identification_types() {
        response = codeValueGeneralController.findAllPatientIdentificationTypes(new GraphQLPage(50, 0));
    }

    @Then("I find identification types")
    public void i_find_identification_types() {
        assertTrue(response.getTotalElements() > 0);
        IdentificationMother.IDENTIFICATION_CODE_LIST.forEach(idCode -> {
            assertTrue(response.getContent().stream()
                    .filter(e -> e.getId().getCode().equalsIgnoreCase(idCode))
                    .findFirst()
                    .isPresent());
        });
    }

}
