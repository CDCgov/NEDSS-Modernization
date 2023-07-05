package gov.cdc.nbs.questionbank.page.model;

import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.Test;
import gov.cdc.nbs.questionbank.page.model.PageSummary.EventType;

class PageSummaryTest {

    @Test
    void should_set_event_type_investigation() {
        EventType eventType = new EventType("INV");
        assertEquals("Investigation", eventType.display());
    }

    @Test
    void should_set_event_type_contact() {
        EventType eventType = new EventType("CON");
        assertEquals("Contact", eventType.display());
    }

    @Test
    void should_set_event_type_vaccination() {
        EventType eventType = new EventType("VAC");
        assertEquals("Vaccination", eventType.display());
    }

    @Test
    void should_set_event_type_interview() {
        EventType eventType = new EventType("IXS");
        assertEquals("Interview", eventType.display());
    }

    @Test
    void should_set_event_type_lab_susceptibility() {
        EventType eventType = new EventType("SUS");
        assertEquals("Lab Susceptibility", eventType.display());
    }

    @Test
    void should_set_event_type_lab() {
        EventType eventType = new EventType("LAB");
        assertEquals("Lab Report", eventType.display());
    }

    @Test
    void should_set_event_type_isolate() {
        EventType eventType = new EventType("ISO");
        assertEquals("Lab Isolate Tracking", eventType.display());
    }

    @Test
    void should_set_event_display() {
        EventType eventType = new EventType("something different");
        assertEquals("something different", eventType.display());
    }
}


