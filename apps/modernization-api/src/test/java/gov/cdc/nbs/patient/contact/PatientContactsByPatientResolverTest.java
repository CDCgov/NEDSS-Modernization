package gov.cdc.nbs.patient.contact;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PatientContactsByPatientResolverTest {

    @Test
    void should_return_named_contacts_by_patient() {

        ContactNamedByPatientFinder contactNamedByPatientFinder = mock(ContactNamedByPatientFinder.class);

        when(contactNamedByPatientFinder.find(anyLong())).thenReturn(
                List.of(
                        new PatientContacts.NamedByPatient(
                                2131L,
                                Instant.parse("2023-05-17T22:13:43Z"),
                                new PatientContacts.NamedContact(
                                        3733L,
                                        "contact-name-value"
                                ),
                                Instant.parse("2023-02-15T11:13:17Z"),
                                "priority-value",
                                "disposition-value",
                                "event-value"

                        )
                )
        );

        PatientContactsByPatientResolver resolver = new PatientContactsByPatientResolver(contactNamedByPatientFinder);

        PatientContacts actual = resolver.find(811L);

        assertThat(actual)
                .returns(811L, PatientContacts::patient);

        assertThat(actual.namedByPatient()).satisfiesExactly(
                actual_contact ->
                        assertAll(
                                () -> assertThat(actual_contact)
                                        .returns(2131L, PatientContacts.NamedByPatient::contactRecord)
                                        .returns("priority-value", PatientContacts.NamedByPatient::priority)
                                        .returns("disposition-value", PatientContacts.NamedByPatient::disposition)
                                        .returns("event-value", PatientContacts.NamedByPatient::event),
                                () -> assertThat(actual_contact.contact())
                                        .returns(3733L, PatientContacts.NamedContact::id)
                                        .returns("contact-name-value", PatientContacts.NamedContact::name),
                                () -> assertThat(actual_contact.createdOn()).isEqualTo("2023-05-17T22:13:43Z"),
                                () -> assertThat(actual_contact.namedOn()).isEqualTo("2023-02-15T11:13:17Z")
                        )
        );
    }
}