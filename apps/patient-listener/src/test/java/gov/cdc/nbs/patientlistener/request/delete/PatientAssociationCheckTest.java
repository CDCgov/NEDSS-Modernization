package gov.cdc.nbs.patientlistener.request.delete;

import gov.cdc.nbs.message.patient.event.PatientRequest;
import gov.cdc.nbs.patientlistener.request.PatientRequestException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PatientAssociationCheckTest {

    @Test
    void should_pass_check_for_patient_without_active_revisions() {
        PatientAssociationCountFinder validator = mock(PatientAssociationCountFinder.class);

        when(validator.count(anyLong())).thenReturn(0L);

        PatientAssociationCheck check = new PatientAssociationCheck(validator);

        PatientRequest.Delete request = new PatientRequest.Delete("key", 823L, 307L);

        check.check(request);
    }

    @Test
    void should_not_pass_check_for_patient_with_active_revisions() {
        PatientAssociationCountFinder validator = mock(PatientAssociationCountFinder.class);

        when(validator.count(anyLong())).thenReturn(5L);

        PatientAssociationCheck check = new PatientAssociationCheck(validator);

        PatientRequest.Delete request = new PatientRequest.Delete("key", 823L, 307L);

        assertThatThrownBy(() -> check.check(request))
            .isInstanceOf(PatientRequestException.class)
            .hasMessage("Cannot delete patient with Active Revisions");
    }
}
