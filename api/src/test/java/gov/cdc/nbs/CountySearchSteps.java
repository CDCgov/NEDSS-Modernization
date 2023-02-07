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

import gov.cdc.nbs.controller.CountyController;
import gov.cdc.nbs.entity.srte.StateCountyCodeValue;
import gov.cdc.nbs.graphql.GraphQLPage;
import gov.cdc.nbs.repository.StateCodeRepository;
import gov.cdc.nbs.repository.StateCountyCodeRepository;
import gov.cdc.nbs.support.CountyMother;
import gov.cdc.nbs.support.StateMother;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
@Rollback(false)
public class CountySearchSteps {

    @Autowired
    private StateCountyCodeRepository stateCountyCodeRepository;
    @Autowired
    private StateCodeRepository stateCodeRepository;
    @Autowired
    private CountyController countyController;

    private Page<StateCountyCodeValue> response;

    @Given("Counties exist")
    public void counties_exist() {
        // a state must exist for counties to be queryable
        var georgia = StateMother.georgia();
        if (!stateCodeRepository.existsById(georgia.getId())) {
            stateCodeRepository.save(georgia);
        }
        var dekalb = CountyMother.dekalbGA();
        if (!stateCountyCodeRepository.existsById(dekalb.getId())) {
            stateCountyCodeRepository.save(dekalb);
        }

        var tennessee = StateMother.tennessee();
        if (!stateCodeRepository.existsById(tennessee.getId())) {
            stateCodeRepository.save(tennessee);
        }
        var monroe = CountyMother.monroeTN();
        if (!stateCountyCodeRepository.existsById(monroe.getId())) {
            stateCountyCodeRepository.save(monroe);
        }
    }

    @When("I search for counties")
    public void i_search_for_counties() {
        response = countyController.findAllCountyCodesForState(StateMother.georgia().getId(), new GraphQLPage(50, 0));
    }

    @Then("I find counties")
    public void i_find_counties() {
        assertTrue(response.getTotalElements() > 0);
        // Should contain dekalb as we queried for GA
        var dekalb = response.getContent()
                .stream()
                .filter(c -> c.getId().equals(CountyMother.dekalbGA().getId()))
                .findFirst();
        assertTrue(dekalb.isPresent());

        // Should not contain monroe as we queried for GA
        var monroe = response.getContent()
                .stream()
                .filter(c -> c.getId().equals(CountyMother.monroeTN().getId()))
                .findFirst();
        assertTrue(monroe.isEmpty());
    }

}
