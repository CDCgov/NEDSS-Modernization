package gov.cdc.nbs.questionbank.page;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import java.time.Instant;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import com.querydsl.core.Tuple;
import gov.cdc.nbs.authentication.entity.QAuthUser;
import gov.cdc.nbs.questionbank.entity.QCodeValueGeneral;
import gov.cdc.nbs.questionbank.entity.QWaTemplate;
import gov.cdc.nbs.questionbank.page.model.PageSummary;

class PageSummaryMapperTest {

    private final PageSummaryMapper mapper = new PageSummaryMapper();

    @Test
    void should_set_event_type_investigation() {
        PageSummary.EventType eventType = mapper.getEventType("INV");
        assertEquals("Investigation", eventType.name());
    }

    @Test
    void should_set_event_type_contact() {
        PageSummary.EventType eventType = mapper.getEventType("CON");
        assertEquals("Contact", eventType.name());
    }

    @Test
    void should_set_event_type_vaccination() {
        PageSummary.EventType eventType = mapper.getEventType("VAC");
        assertEquals("Vaccination", eventType.name());
    }

    @Test
    void should_set_event_type_interview() {
        PageSummary.EventType eventType = mapper.getEventType("IXS");
        assertEquals("Interview", eventType.name());
    }

    @Test
    void should_set_event_type_lab_susceptibility() {
        PageSummary.EventType eventType = mapper.getEventType("SUS");
        assertEquals("Lab Susceptibility", eventType.name());
    }

    @Test
    void should_set_event_type_lab() {
        PageSummary.EventType eventType = mapper.getEventType("LAB");
        assertEquals("Lab Report", eventType.name());
    }

    @Test
    void should_set_event_type_isolate() {
        PageSummary.EventType eventType = mapper.getEventType("ISO");
        assertEquals("Lab Isolate Tracking", eventType.name());
    }


    @ParameterizedTest
    @CsvSource({
            "Draft,0,Published with Draft",
            "Draft,null,Initial Draft",
            "Published,0,Published",
    })
    void should_set_status(String templateType, String versionNbr, String expected) {
        Tuple tuple = Mockito.mock(Tuple.class);
        when(tuple.get(QWaTemplate.waTemplate.templateType)).thenReturn(templateType);
        Integer version = "null".equals(versionNbr) ? null : Integer.parseInt(versionNbr);
        when(tuple.get(QWaTemplate.waTemplate.publishVersionNbr)).thenReturn(version);

        String status = mapper.getStatus(tuple);
        assertEquals(expected, status);
    }

    @Test
    void should_set_event_display() {
        PageSummary.EventType eventType = mapper.getEventType("something different");
        assertEquals("something different", eventType.name());
    }

    @Test
    void should_set_messageMappingGuide() {
        final QWaTemplate template = QWaTemplate.waTemplate;
        final QCodeValueGeneral cvg = QCodeValueGeneral.codeValueGeneral;
        final QAuthUser user = QAuthUser.authUser;

        Instant now = Instant.now();

        Tuple tuple = Mockito.mock(Tuple.class);
        when(tuple.get(template.id)).thenReturn(1L);
        when(tuple.get(template.busObjType)).thenReturn("INV");
        when(tuple.get(template.templateNm)).thenReturn("template name");
        when(tuple.get(template.descTxt)).thenReturn("template description");
        when(tuple.get(template.templateType)).thenReturn("Draft");
        when(tuple.get(template.publishVersionNbr)).thenReturn(null);

        when(tuple.get(template.nndEntityIdentifier)).thenReturn("mmgId");
        when(tuple.get(cvg.codeShortDescTxt)).thenReturn("mmg description");
        when(tuple.get(template.lastChgTime)).thenReturn(now);
        when(tuple.get(template.lastChgUserId)).thenReturn(2L);
        when(tuple.get(user.userFirstNm)).thenReturn("first");
        when(tuple.get(user.userLastNm)).thenReturn("last");

        PageSummary summary = mapper.toPageSummary(tuple);
        assertEquals("Investigation", summary.eventType().name());
        assertEquals("INV", summary.eventType().value());
        assertEquals("template name", summary.name());
        assertEquals("template description", summary.description());
        assertEquals("Initial Draft", summary.status());
        assertEquals("mmgId", summary.messageMappingGuide().value());
        assertEquals("mmg description", summary.messageMappingGuide().name());
        assertEquals(now, summary.lastUpdate());
        assertEquals("first last", summary.lastUpdateBy());
    }

}
