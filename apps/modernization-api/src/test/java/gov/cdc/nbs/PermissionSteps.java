package gov.cdc.nbs;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import gov.cdc.nbs.config.security.NbsAuthority;
import gov.cdc.nbs.config.security.NbsUserDetails;
import gov.cdc.nbs.config.security.SecurityProperties;
import gov.cdc.nbs.controller.EventController;
import gov.cdc.nbs.controller.PatientController;
import gov.cdc.nbs.entity.enums.RecordStatus;
import gov.cdc.nbs.graphql.GraphQLPage;
import gov.cdc.nbs.graphql.filter.InvestigationFilter;
import gov.cdc.nbs.graphql.filter.LabReportFilter;
import gov.cdc.nbs.graphql.filter.OrganizationFilter;
import gov.cdc.nbs.graphql.filter.PatientFilter;
import gov.cdc.nbs.investigation.InvestigationResolver;
import gov.cdc.nbs.repository.ProgramAreaCodeRepository;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class PermissionSteps {
    @Autowired
    private SecurityProperties properties;
    @Autowired
    private Algorithm algorithm;
    @Autowired
    PatientController patientController;
    @Autowired
    ProgramAreaCodeRepository programAreaCodeRepository;
    @Autowired
    EventController eventController;
    @Autowired
    InvestigationResolver investigationResolver;

    @Before
    public void clearAuth() {
        SecurityContextHolder.getContext().setAuthentication(null);
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
            var nbsUserDetails = NbsUserDetails.builder()
                    .id(1L)
                    .username("MOCK-USER")
                    .token(createToken("MOCK-USER"))
                    .authorities(nbsAuthorities)
                    .isEnabled(true)
                    .build();
            var pat = new PreAuthenticatedAuthenticationToken(nbsUserDetails, null, nbsUserDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(pat);

        } else {
            // add authority to existing userDetails
            var existingUserDetails = (NbsUserDetails) currentAuth.getPrincipal();
            var existingAuthorities = existingUserDetails.getAuthorities();
            if (existingAuthorities == null) {
                existingAuthorities = new HashSet<NbsAuthority>();
            }
            existingAuthorities.addAll(nbsAuthorities);
            var nbsUserDetails = NbsUserDetails.builder()
                    .id(existingUserDetails.getId())
                    .username(existingUserDetails.getUsername())
                    .token(existingUserDetails.getToken())
                    .authorities(existingAuthorities)
                    .isEnabled(existingUserDetails.isEnabled())
                    .build();
            var pat = new PreAuthenticatedAuthenticationToken(nbsUserDetails, null,
                    nbsUserDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(pat);
        }
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
                    response = eventController.findLabReportsByFilter(new LabReportFilter(), page);
                    break;
                case "findPatientsByOrganization":
                    var orgFilter = new OrganizationFilter();
                    response = patientController.findPatientsByOrganizationFilter(orgFilter, page);
                    break;
                case "findAllPatients":
                    response = patientController.findAllPatients(page);
                    break;
                case "findPatientById":
                    response = patientController.findPatientById(1L);
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

    private String createToken(String username) {
        Instant now = Instant.now();
        Instant expiry = Instant.now().plus(Duration.ofMillis(properties.getTokenExpirationMillis()));
        return JWT.create()
                .withIssuer(properties.getTokenIssuer())
                .withIssuedAt(now)
                .withExpiresAt(expiry)
                .withSubject(username)
                .sign(algorithm);
    }

}
