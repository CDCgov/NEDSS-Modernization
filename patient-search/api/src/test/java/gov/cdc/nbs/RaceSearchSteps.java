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
import gov.cdc.nbs.support.RaceMother;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
@Rollback(false)
public class RaceSearchSteps {

    @Autowired
    private CodeValueGeneralRepository codeValueGeneralRepository;
    @Autowired
    private CodeValueGeneralController codeValueGeneralController;

    private Page<CodeValueGeneral> response;

    @Given("Races exist")
    public void races_exist() {
        var asian = RaceMother.asian();
        if (!codeValueGeneralRepository.existsById(asian.getId())) {
            codeValueGeneralRepository.save(asian);
        }
        var blackOrAfricanAmerican = RaceMother.blackOrAfricanAmerican();
        if (!codeValueGeneralRepository.existsById(blackOrAfricanAmerican.getId())) {
            codeValueGeneralRepository.save(blackOrAfricanAmerican);
        }
        var white = RaceMother.white();
        if (!codeValueGeneralRepository.existsById(white.getId())) {
            codeValueGeneralRepository.save(white);
        }
    }

    @When("I search for races")
    public void i_search_for_races() {
        response = codeValueGeneralController.findAllRaceValues(new GraphQLPage(50, 0));
    }

    @Then("I find races")
    public void i_find_races() {
        assertTrue(response.getTotalElements() > 0);
        RaceMother.RACE_LIST.forEach(raceCode -> {
            assertTrue(response.getContent().stream()
                    .filter(e -> e.getId().getCode().equalsIgnoreCase(raceCode))
                    .findFirst()
                    .isPresent());
        });
    }

}
