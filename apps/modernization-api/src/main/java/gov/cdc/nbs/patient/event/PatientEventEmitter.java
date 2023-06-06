package gov.cdc.nbs.patient.event;

import gov.cdc.nbs.message.patient.event.PatientEvent;


public interface PatientEventEmitter {

    void emit(final PatientEvent event);
}
