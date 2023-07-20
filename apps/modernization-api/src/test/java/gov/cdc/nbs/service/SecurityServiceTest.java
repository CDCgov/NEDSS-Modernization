package gov.cdc.nbs.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import gov.cdc.nbs.authentication.NbsAuthority;
import gov.cdc.nbs.authentication.NbsUserDetails;
import gov.cdc.nbs.entity.srte.JurisdictionCode;
import gov.cdc.nbs.repository.JurisdictionCodeRepository;

@ExtendWith(MockitoExtension.class)
class SecurityServiceTest {

    @Mock
    private JurisdictionCodeRepository jurisdictionCodeRepository;

    @InjectMocks
    private SecurityService securityService;

    @Test
    void should_get_the_current_users_oids() {
        // given two jurisdictions exist
        when(jurisdictionCodeRepository.findAll()).thenReturn(Arrays.asList(fultonCounty(), gwinnettCounty()));

        // and the user has access to program area id 1 and the jurisdiciton "ALL"
        NbsUserDetails userDetails = userDetails(allProgramAreas());

        // and the user is logged in
        var pat = new PreAuthenticatedAuthenticationToken(
            userDetails,
            null,
            userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(pat);

        // when compiling program_jurisdiction_oids
        Set<Long> oids = securityService.getProgramAreaJurisdictionOids(userDetails);

        // then the expected oids are returned
        assertNotNull(oids);
        assertEquals(2, oids.size());
        assertTrue(oids.contains(1300100095L));
        assertTrue(oids.contains(1300300095L));
    }


    @Test
    void should_return_oids_for_all_jurisdiction() {
        // given two jurisdictions exist
        when(jurisdictionCodeRepository.findAll()).thenReturn(Arrays.asList(fultonCounty(), gwinnettCounty()));

        // and the user has access to program area id 95 and the jurisdiciton "ALL"
        NbsUserDetails userDetails = userDetails(allProgramAreas());

        // when compiling program_jurisdiction_oids
        Set<Long> oids = securityService.getProgramAreaJurisdictionOids(userDetails);

        // then the expected oids are returned
        assertNotNull(oids);
        assertEquals(2, oids.size());
        assertTrue(oids.contains(1300100095L));
        assertTrue(oids.contains(1300300095L));
    }

    @Test
    void should_return_oids_for_specific_jurisdiction() {
        // given two jurisdictions exist
        when(jurisdictionCodeRepository.findAll()).thenReturn(Arrays.asList(fultonCounty(), gwinnettCounty()));

        // and the user has access to program area id 95 and the jurisdiciton "130001"
        NbsUserDetails userDetails = userDetails(oneProgramArea());

        // when compiling program_jurisdiction_oids
        Set<Long> oids = securityService.getProgramAreaJurisdictionOids(userDetails);

        // then the expected oids are returned
        assertNotNull(oids);
        assertEquals(1, oids.size());
        assertTrue(oids.contains(1300100095L));
    }

    private JurisdictionCode fultonCounty() {
        Instant now = Instant.now();
        return new JurisdictionCode(
                "130001",
                "ALL",
                "GA",
                "GA State",
                "Fulton County",
                "Fulton County",
                now,
                null,
                (short) 1,
                'Y',
                null,
                "13",
                "A",
                now,
                "S_JURDIC_C",
                (short) 1,
                13001,
                null,
                "L",
                "Local",
                null,
                null);
    }

    private JurisdictionCode gwinnettCounty() {
        Instant now = Instant.now();
        return new JurisdictionCode(
                "130002",
                "ALL",
                "GA",
                "GA State",
                "Gwinnett County",
                "Gwinnett County",
                now,
                null,
                (short) 1,
                'Y',
                null,
                "13",
                "A",
                now,
                "S_JURDIC_C",
                (short) 1,
                13003,
                null,
                "L",
                "Local",
                null,
                null);
    }

    private NbsUserDetails userDetails(Set<NbsAuthority> authorities) {
        return new NbsUserDetails(
                null,
                null,
                null,
                null,
                false,
                false,
                null,
                null,
                authorities,
                null,
                false);
    }


    private Set<NbsAuthority> allProgramAreas() {
        Set<NbsAuthority> authorities = new HashSet<>();

        authorities.add(new NbsAuthority(
                null,
                null,
                null,
                95,
                "ALL",
                null));

        return authorities;
    }

    private Set<NbsAuthority> oneProgramArea() {
        Set<NbsAuthority> authorities = new HashSet<>();

        authorities.add(new NbsAuthority(
                null,
                null,
                null,
                95,
                "130001",
                null));

        return authorities;
    }

}
