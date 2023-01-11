package gov.cdc.nbs;

import static org.junit.Assert.assertTrue;

import java.text.ParseException;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import gov.cdc.nbs.controller.LabResultController;
import gov.cdc.nbs.entity.srte.LabResult;
import gov.cdc.nbs.entity.srte.SnomedCode;
import gov.cdc.nbs.graphql.GraphQLPage;
import gov.cdc.nbs.repository.LabCodingSystemRepository;
import gov.cdc.nbs.repository.LabResultRepository;
import gov.cdc.nbs.repository.SnomedCodeRepository;
import gov.cdc.nbs.support.LabResultMother;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
@Rollback(false)
public class CodedResultsSearchSteps {

    @Autowired
    private LabResultRepository labResultRepository;
    @Autowired
    private LabCodingSystemRepository labCodingSystemRepository;
    @Autowired
    private LabResultController labResultController;
    @Autowired
    private SnomedCodeRepository snomedCodeRepository;

    private Page<LabResult> localResponse;
    private Page<SnomedCode> snomedResponse;

    @Given("local coded results exist")
    public void local_coded_results_exist() {
        createLocalResultIfNotExists(LabResultMother.localAbnormal());
        createLocalResultIfNotExists(LabResultMother.localUnexplained());
    }

    @Given("snomed coded results exist")
    public void snomed_coded_results_exist() throws ParseException {
        createSnomedResultIfNotExists(LabResultMother.snomedAbnormal());
        createSnomedResultIfNotExists(LabResultMother.snomedNoGrowth());
    }

    @When("I search for local coded results by {string}")
    public void i_search_for_local_coded_results_by_(String searchText) {
        localResponse = labResultController.findLocalCodedResults(searchText, new GraphQLPage(10, 0));
    }

    @Then("A local coded result is {string}")
    public void a_local_coded_result_is(String expectedResult) {
        switch (expectedResult) {
            case "found":
                assertTrue(localResponse.getTotalElements() > 0);
                break;
            case "nout found":
                assertTrue(localResponse.getTotalElements() == 0);
                break;
        }
    }

    @When("I search for snomed coded results by {string}")
    public void i_search_for_snomed_coded_results_by_(String searchText) {
        snomedResponse = labResultController.findSnomedCodedResults(searchText, new GraphQLPage(10, 0));
    }

    @Then("A snomed coded results is {string}")
    public void a_snomed_coded_result_is(String expectedResult) {
        switch (expectedResult) {
            case "found":
                assertTrue(snomedResponse.getTotalElements() > 0);
                break;
            case "nout found":
                assertTrue(snomedResponse.getTotalElements() == 0);
                break;
        }
    }

    private void createLocalResultIfNotExists(LabResult result) {
        if (!labResultRepository.existsById(result.getId())) {
            if (!labCodingSystemRepository.existsById(result.getLaboratory().getId())) {
                labCodingSystemRepository.save(result.getLaboratory());
            }
            labResultRepository.save(result);
        }
    }

    private void createSnomedResultIfNotExists(SnomedCode code) {
        if (!snomedCodeRepository.existsById(code.getId())) {
            snomedCodeRepository.save(code);
        }
    }
}
