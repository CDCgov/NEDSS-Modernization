package gov.cdc.nbs.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;
import org.springframework.web.servlet.view.RedirectView;
import gov.cdc.nbs.event.search.InvestigationFilter;
import gov.cdc.nbs.event.search.LabReportFilter;
import gov.cdc.nbs.graphql.filter.PatientFilter;
import gov.cdc.nbs.redirect.search.EventFilterResolver;
import gov.cdc.nbs.redirect.search.PatientFilterFromRequestParamResolver;
import gov.cdc.nbs.service.EncryptionService;

@ExtendWith(MockitoExtension.class)
class RedirectControllerTest {

    @Mock
    private PatientFilterFromRequestParamResolver patientFilterFromRequestParamResolver;

    @Mock
    private EventFilterResolver eventFilterResolver;

    @Mock
    private EncryptionService encryptionService;

    @InjectMocks
    private RedirectController controller;

    @Test
    void should_return_encrypted_event_filter_when_both_are_valid() {
        // Given a request containing patient filter and event data
        Map<String, String> params = Map.of("key", "value");
        RedirectAttributes attributes = new RedirectAttributesModelMap();
        InvestigationFilter investigationFilter = new InvestigationFilter();
        when(eventFilterResolver.resolve(params)).thenReturn(investigationFilter);

        // And a working encryption service
        when(encryptionService.handleEncryption(investigationFilter)).thenReturn("encrypted");

        // When a redirect is created
        RedirectView redirect = controller.redirectSimpleSearch(attributes, params);

        // Then it contains the encrypted patient filter
        assertNotNull(redirect);
        assertEquals("encrypted", attributes.getAttribute("q"));
        assertEquals("investigation", attributes.getAttribute("type"));
    }

    @Test
    void should_return_encrypted_patient_filter() {
        // Given a request containing patient filter data
        Map<String, String> params = Map.of("key", "value");
        RedirectAttributes attributes = new RedirectAttributesModelMap();
        PatientFilter filter = new PatientFilter();
        when(patientFilterFromRequestParamResolver.resolve(params)).thenReturn(filter);

        // And a working encryption service
        when(encryptionService.handleEncryption(filter)).thenReturn("encrypted");

        // When a redirect is created
        RedirectView redirect = controller.redirectSimpleSearch(attributes, params);

        // Then it contains the encrypted patient filter
        assertNotNull(redirect);
        assertEquals("encrypted", attributes.getAttribute("q"));
        assertNull(attributes.getAttribute("type"));
    }

    @Test
    void should_return_encrypted_investigation_filter() {
        // Given a request containing investigation filter data
        Map<String, String> params = Map.of("key", "value");
        RedirectAttributes attributes = new RedirectAttributesModelMap();
        InvestigationFilter filter = new InvestigationFilter();
        when(eventFilterResolver.resolve(params)).thenReturn(filter);

        // And a working encryption service
        when(encryptionService.handleEncryption(filter)).thenReturn("encrypted");

        // When a redirect is created
        RedirectView redirect = controller.redirectSimpleSearch(attributes, params);

        // Then it contains the encrypted investigation filter and type
        assertNotNull(redirect);
        assertEquals("encrypted", attributes.getAttribute("q"));
        assertEquals("investigation", attributes.getAttribute("type"));
    }

    @Test
    void should_return_encrypted_labreport_filter() {
        // Given a request containing lab report filter data
        Map<String, String> params = Map.of("key", "value");
        RedirectAttributes attributes = new RedirectAttributesModelMap();
        LabReportFilter filter = new LabReportFilter();
        when(eventFilterResolver.resolve(params)).thenReturn(filter);

        // And a working encryption service
        when(encryptionService.handleEncryption(filter)).thenReturn("encrypted");

        // When a redirect is created
        RedirectView redirect = controller.redirectSimpleSearch(attributes, params);

        // Then it contains the encrypted lab filter and type
        assertNotNull(redirect);
        assertEquals("encrypted", attributes.getAttribute("q"));
        assertEquals("labReport", attributes.getAttribute("type"));
    }

    @Test
    void should_return_empty_params() {
        // Given a request containing no filter data
        Map<String, String> params = Map.of("key", "value");
        RedirectAttributes attributes = new RedirectAttributesModelMap();

        // When a redirect is created
        RedirectView redirect = controller.redirectSimpleSearch(attributes, params);

        // Then it contains the encrypted patient filter
        assertNotNull(redirect);
        assertNull(attributes.getAttribute("q"));
        assertNull(attributes.getAttribute("type"));
    }
}
