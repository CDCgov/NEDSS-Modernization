package gov.cdc.nbs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import gov.cdc.nbs.controller.LabTestController;
import gov.cdc.nbs.entity.srte.LabTest;
import gov.cdc.nbs.entity.srte.LoincCode;
import gov.cdc.nbs.graphql.GraphQLPage;
import gov.cdc.nbs.repository.LabCodingSystemRepository;
import gov.cdc.nbs.repository.LabTestRepository;
import gov.cdc.nbs.repository.LoincCodeRepository;
import gov.cdc.nbs.support.LabTestMother;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
@Rollback(false)
public class ResultedTestSearchSteps {

    @Autowired
    private LabTestRepository labTestRepository;
    @Autowired
    private LoincCodeRepository loincCodeRepository;
    @Autowired
    private LabCodingSystemRepository labCodingSystemRepository;
    @Autowired
    private LabTestController labTestController;

    private Page<LabTest> localTestResponse;
    private Page<LoincCode> loincTestResponse;

    @Given("local resulted tests exist")
    public void resulted_tests_exist() {
        createLocalTestIfNotExists(LabTestMother.localTestThyroxine());
        createLocalTestIfNotExists(LabTestMother.localTestHiv1());
    }

    @Given("loinc resulted tests exist")
    public void loinc_resulted_tests_exist() {
        createLoincTestIfNotExists(LabTestMother.loincTestAcyclovir());
        createLoincTestIfNotExists(LabTestMother.loincTestAmdinocillin());
    }

    @When("I search for local resulted tests by {string}")
    public void i_search_for_resulted_test(String searchText) {
        localTestResponse = labTestController.findLocalLabTest(searchText, new GraphQLPage(10, 0));
    }

    @When("I search for loinc resulted tests by {string}")
    public void i_search_for_loinc_test(String searchText) {
        loincTestResponse = labTestController.findLoincLabTest(searchText, new GraphQLPage(10, 0));
    }

    @Then("A local test is {string}")
    public void a_local_test_is(String expectedResult) {
        switch (expectedResult) {
            case "found":
                assertTrue(localTestResponse.getTotalElements() > 0);
                break;
            case "nout found":
                assertEquals(0, localTestResponse.getTotalElements());
                break;
        }
    }

    @Then("A loinc test is {string}")
    public void a_loinc_test_is(String expectedResult) {
        switch (expectedResult) {
            case "found":
                assertTrue(loincTestResponse.getTotalElements() > 0);
                break;
            case "nout found":
                assertEquals(0, loincTestResponse.getTotalElements());
                break;
        }
    }

    private void createLocalTestIfNotExists(LabTest test) {
        if (!labTestRepository.existsById(test.getId())) {
            if (!labCodingSystemRepository.existsById(test.getLaboratory().getId())) {
                labCodingSystemRepository.save(test.getLaboratory());
            }
            labTestRepository.save(test);
        }
    }

    private void createLoincTestIfNotExists(LoincCode test) {
        if (!loincCodeRepository.existsById(test.getId())) {
            loincCodeRepository.save(test);
        }
    }

}
