package gov.cdc.nbs.patient.profile.redirect.incoming;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import jakarta.servlet.http.Cookie;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class IncomingPatientIdentifierResolverTest {

    @Test
    void should_return_master_patient_record_identifier_from_query_parameter() {

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addParameter("MPRUid", "2803");

        IncomingPatientIdentifierResolver resolver = new IncomingPatientIdentifierResolver();

        Optional<Long> actual = resolver.fromQueryParams(request);

        assertThat(actual).contains(2803L);
    }

    @Test
    void should_return_patient_identifier_from_query_parameter() {

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addParameter("uid", "2069");

        IncomingPatientIdentifierResolver resolver = new IncomingPatientIdentifierResolver();

        Optional<Long> actual = resolver.fromQueryParams(request);

        assertThat(actual).contains(2069L);
    }

    @Test
    void should_prefer_master_patient_record_identifier_over_patient_identifier_from_query_parameter() {

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addParameter("MPRUid", "2803");
        request.addParameter("uid", "2069");

        IncomingPatientIdentifierResolver resolver = new IncomingPatientIdentifierResolver();

        Optional<Long> actual = resolver.fromQueryParams(request);

        assertThat(actual).contains(2803L);
    }

    @Test
    void should_return_empty_when_master_patient_record_identifier_is_not_a_number() {

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addParameter("MPRUid", "other-value");

        IncomingPatientIdentifierResolver resolver = new IncomingPatientIdentifierResolver();

        Optional<Long> actual = resolver.fromQueryParams(request);

        assertThat(actual).isNotPresent();
    }

    @Test
    void should_return_empty_when_patient_identifier_is_not_a_number() {

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addParameter("uid", "other-value");

        IncomingPatientIdentifierResolver resolver = new IncomingPatientIdentifierResolver();

        Optional<Long> actual = resolver.fromQueryParams(request);

        assertThat(actual).isNotPresent();
    }

    @Test
    void should_return_returning_patient_identifier_from_cookie() {

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setCookies(new Cookie("Returning-Patient", "2803"));

        IncomingPatientIdentifierResolver resolver = new IncomingPatientIdentifierResolver();

        Optional<Long> actual = resolver.fromReturningPatient(request);

        assertThat(actual).contains(2803L);

    }

    @Test
    void should_return_empty_when_returning_patient_identifier_cookie_is_not_present() {

        MockHttpServletRequest request = new MockHttpServletRequest();

        IncomingPatientIdentifierResolver resolver = new IncomingPatientIdentifierResolver();

        Optional<Long> actual = resolver.fromReturningPatient(request);

        assertThat(actual).isNotPresent();
    }

    @Test
    void should_return_empty_when_returning_patient_identifier_cookie_is_not_a_number() {

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setCookies(new Cookie("Returning-Patient", "other-value"));

        IncomingPatientIdentifierResolver resolver = new IncomingPatientIdentifierResolver();

        Optional<Long> actual = resolver.fromReturningPatient(request);

        assertThat(actual).isNotPresent();
    }
}
