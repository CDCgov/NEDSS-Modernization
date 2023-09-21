package gov.cdc.nbs;

import gov.cdc.nbs.authentication.NbsAuthority;
import gov.cdc.nbs.authentication.NbsUserDetails;
import gov.cdc.nbs.authorization.ActiveUser;
import gov.cdc.nbs.entity.enums.RecordStatus;
import gov.cdc.nbs.event.search.InvestigationFilter;
import gov.cdc.nbs.event.search.LabReportFilter;
import gov.cdc.nbs.event.search.investigation.InvestigationResolver;
import gov.cdc.nbs.event.search.labreport.LabReportResolver;
import gov.cdc.nbs.graphql.GraphQLPage;
import gov.cdc.nbs.graphql.filter.PatientFilter;
import gov.cdc.nbs.patient.PatientController;
import gov.cdc.nbs.repository.ProgramAreaCodeRepository;
import gov.cdc.nbs.support.TestActive;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@Transactional
public class PermissionSteps {

    @Autowired
    TestActive<ActiveUser> activeUser;

    @Autowired
    PatientController patientController;
    @Autowired
    ProgramAreaCodeRepository programAreaCodeRepository;
    @Autowired
    LabReportResolver labReportResolver;
    @Autowired
    InvestigationResolver investigationResolver;

    @Autowired
    TestActive<UserDetails> activeUserDetails;

    @Before
    public void clearAuth() {
        SecurityContextHolder.getContext().setAuthentication(null);
        activeUserDetails.reset();
    }

    private Object response;
    private AccessDeniedException exception;

    @Given("I have the authorities: {string} for the jurisdiction: {string} and program area: {string}")
    public void i_have_the_authority(String authoritiesString, String jurisdiction, String programArea) {
        var authorities = authoritiesString.split(",");
        var nbsAuthorities = new HashSet<NbsAuthority>();
        var programAreas = programAreaCodeRepository.findAll();
        var programAreaEntry = programAreas.stream().filter(f -> f.getId().equals(programArea)).findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Unable to find program area: " + programArea));
        for (var authority : authorities) {
            // Create a NbsAuthority object based on provided input
            var operationObject = authority.trim().split("-");
            var operation = operationObject.length > 0 ? operationObject[0] : null;
            var object = operationObject.length > 1 ? operationObject[1] : null;
            nbsAuthorities.add(NbsAuthority.builder()
                .businessOperation(operation)
                .businessObject(object)
                .authority(authority.trim())
                .jurisdiction(jurisdiction)
                .programArea(programArea)
                .programAreaUid(programAreaEntry.getNbsUid())
                .build());
        }

        var currentAuth = SecurityContextHolder.getContext().getAuthentication();
        if (currentAuth == null) {
            // no auth is set, create a new NbsUserDetails object and set the authentication
            // on the securityContextHolder

            //  The active user is set by the "I am logged into NBS and a security log entry exists" step and is not
            //  always called in a feature.  When a user is not active then just give the ID of 1.  Will fix the features
            //  to always activate a user.
            long id = activeUser.maybeActive()
                .map(ActiveUser::id)
                .orElse(1L);

            var nbsUserDetails = NbsUserDetails.builder()
                .id(id)
                .username("MOCK-USER")
                .authorities(nbsAuthorities)
                .isEnabled(true)
                .build();
            applyUserDetails(nbsUserDetails);

        } else {
            // add authority to existing userDetails
            var existingUserDetails = (NbsUserDetails) currentAuth.getPrincipal();
            var existingAuthorities = existingUserDetails.getAuthorities();
            if (existingAuthorities == null) {
                existingAuthorities = new HashSet<>();
            }
            existingAuthorities.addAll(nbsAuthorities);
            var nbsUserDetails = NbsUserDetails.builder()
                .id(existingUserDetails.getId())
                .username(existingUserDetails.getUsername())
                .authorities(existingAuthorities)
                .isEnabled(existingUserDetails.isEnabled())
                .build();

            applyUserDetails(nbsUserDetails);
        }
    }

    private void applyUserDetails(final NbsUserDetails userDetails) {
        activeUserDetails.active(userDetails);

        var pat = new PreAuthenticatedAuthenticationToken(
            userDetails,
            null,
            userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(pat);
    }

    @When("I search for a patient by {string}")
    public void i_search_for_a_patient(String searchType) {
        var page = new GraphQLPage(10, 0);
        try {
            switch (searchType) {
                case "findPatientsByFilter":
                    var patientFilter = new PatientFilter(RecordStatus.ACTIVE);
                    patientFilter.setFirstName("John");
                    response = patientController.findPatientsByFilter(patientFilter, page);
                    break;
                case "findInvestigation":
                    response = investigationResolver.findInvestigationsByFilter(new InvestigationFilter(), page);
                    break;
                case "findLabReport":
                    response = labReportResolver.findLabReportsByFilter(new LabReportFilter(), page);
                    break;
                default:
                    throw new RuntimeException("Invalid searchType: " + searchType);
            }
        } catch (AccessDeniedException e) {
            exception = e;
        }
    }

    @Then("a {string} result is returned")
    public void a_response_type_is_returned(String resultType) {
        if (resultType.equals("success")) {
            assertNotNull(response);
            assertNull(exception);
        } else if (resultType.equals("AccessDeniedException")) {
            assertNotNull(exception);
            assertNull(response);
        } else {
            throw new RuntimeException("Invalid responseType specified: " + resultType);
        }
    }

}
