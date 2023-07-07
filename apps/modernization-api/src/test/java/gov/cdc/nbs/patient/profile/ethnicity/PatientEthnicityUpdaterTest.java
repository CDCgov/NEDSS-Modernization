package gov.cdc.nbs.patient.profile.ethnicity;

import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.message.patient.input.EthnicityInput;
import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.patient.RequestContext;
import gov.cdc.nbs.patient.event.PatientEventEmitter;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PatientEthnicityUpdaterTest {

    @Test
    void should_change_patient_ethnicity() {

        Person patient = spy(new Person(121L, "local-id-value"));

        EntityManager entityManager = mock(EntityManager.class);

        when(entityManager.find(eq(Person.class), anyLong())).thenReturn(patient);

        PatientEventEmitter emitter = mock(PatientEventEmitter.class);

        RequestContext context = new RequestContext(131L, Instant.parse("2020-03-03T10:15:30.00Z"));

        EthnicityInput changes = new EthnicityInput();
        changes.setPatient(121L);
        changes.setAsOf(Instant.parse("2012-03-03T10:15:30.00Z"));
        changes.setEthnicGroup("ethnic-group-value");
        changes.setUnknownReason("unknown-reason-value");


        PatientEthnicityUpdater service = new PatientEthnicityUpdater(entityManager, emitter);

        service.update(context, changes);

        ArgumentCaptor<PatientCommand.UpdateEthnicityInfo> captor =
                ArgumentCaptor.forClass(PatientCommand.UpdateEthnicityInfo.class);

        verify(patient).update(captor.capture());

        PatientCommand.UpdateEthnicityInfo actual = captor.getValue();

        assertThat(actual)
                .returns(121L, PatientCommand.UpdateEthnicityInfo::person)
                .returns(Instant.parse("2012-03-03T10:15:30.00Z"), PatientCommand.UpdateEthnicityInfo::asOf)
                .returns("ethnic-group-value", PatientCommand.UpdateEthnicityInfo::ethnicGroup)
                .returns("unknown-reason-value", PatientCommand.UpdateEthnicityInfo::unknownReason)
                .returns(131L, PatientCommand.UpdateEthnicityInfo::requester)
                .returns(Instant.parse("2020-03-03T10:15:30.00Z"), PatientCommand.UpdateEthnicityInfo::requestedOn);
    }

    @Test
    void should_add_new_ethnicity_detail() {
        Person patient = spy(new Person(121L, "local-id-value"));

        EntityManager entityManager = mock(EntityManager.class);

        when(entityManager.find(eq(Person.class), anyLong())).thenReturn(patient);

        PatientEventEmitter emitter = mock(PatientEventEmitter.class);

        RequestContext context = new RequestContext(131L, Instant.parse("2020-03-03T10:15:30.00Z"));

        EthnicityInput changes = new EthnicityInput();
        changes.setPatient(121L);

        changes.setDetailed(List.of("ethnicity-detail"));

        PatientEthnicityUpdater service = new PatientEthnicityUpdater(entityManager, emitter);

        service.update(context, changes);

        ArgumentCaptor<PatientCommand.AddDetailedEthnicity> captor =
                ArgumentCaptor.forClass(PatientCommand.AddDetailedEthnicity.class);

        verify(patient).add(captor.capture());

        PatientCommand.AddDetailedEthnicity actual = captor.getValue();

        assertThat(actual)
                .returns(121L, PatientCommand.AddDetailedEthnicity::person)
                .returns("ethnicity-detail", PatientCommand.AddDetailedEthnicity::ethnicity)
                .returns(131L, PatientCommand.AddDetailedEthnicity::requester)
                .returns(Instant.parse("2020-03-03T10:15:30.00Z"), PatientCommand.AddDetailedEthnicity::requestedOn);
    }

    @Test
    void should_remove_existing_ethnicity_detail() {
        Person patient = spy(new Person(121L, "local-id-value"));

        patient.add(
                new PatientCommand.AddDetailedEthnicity(
                        121L,
                        "another-ethnicity-detail",
                        119L,
                        Instant.parse("2022-05-03T15:11:30Z")));

        patient.add(
                new PatientCommand.AddDetailedEthnicity(
                        121L,
                        "ethnicity-detail",
                        119L,
                        Instant.parse("2022-05-03T15:11:30Z")));

        EntityManager entityManager = mock(EntityManager.class);

        when(entityManager.find(eq(Person.class), anyLong())).thenReturn(patient);

        PatientEventEmitter emitter = mock(PatientEventEmitter.class);

        RequestContext context = new RequestContext(131L, Instant.parse("2020-03-03T10:15:30.00Z"));

        EthnicityInput changes = new EthnicityInput();
        changes.setPatient(121L);
        changes.setDetailed(List.of("another-ethnicity-detail"));

        PatientEthnicityUpdater service = new PatientEthnicityUpdater(entityManager, emitter);

        service.update(context, changes);

        ArgumentCaptor<PatientCommand.RemoveDetailedEthnicity> captor =
                ArgumentCaptor.forClass(PatientCommand.RemoveDetailedEthnicity.class);

        verify(patient).remove(captor.capture());

        PatientCommand.RemoveDetailedEthnicity actual = captor.getValue();

        assertThat(actual)
                .returns(121L, PatientCommand.RemoveDetailedEthnicity::person)
                .returns("ethnicity-detail", PatientCommand.RemoveDetailedEthnicity::ethnicity)
                .returns(131L, PatientCommand.RemoveDetailedEthnicity::requester)
                .returns(Instant.parse("2020-03-03T10:15:30.00Z"), PatientCommand.RemoveDetailedEthnicity::requestedOn);
    }
}
