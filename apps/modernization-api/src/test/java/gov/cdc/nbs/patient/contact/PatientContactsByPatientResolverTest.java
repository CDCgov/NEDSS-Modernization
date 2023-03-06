package gov.cdc.nbs.patient.contact;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PatientContactsByPatientResolverTest {

  @Test
  void should_return_contacts_that_the_patient_named_as_a_contact() {

    ContactNamedByPatientFinder namedByPatientFinder = mock(ContactNamedByPatientFinder.class);

    when(namedByPatientFinder.find(anyLong())).thenReturn(
        List.of(
            new PatientContacts.NamedByPatient(
                2131L,
                Instant.parse("2023-05-17T22:13:43Z"),
                "condition-value",
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

    PatientNamedByContactFinder namedByContactFinder = mock(PatientNamedByContactFinder.class);

    when(namedByContactFinder.find(anyLong())).thenReturn(List.of());

    PatientContactsByPatientResolver resolver = new PatientContactsByPatientResolver(
        namedByPatientFinder,
        namedByContactFinder
    );

    PatientContacts actual = resolver.find(811L);

    assertThat(actual)
        .returns(811L, PatientContacts::patient);

    assertThat(actual.namedByPatient()).hasSize(1);
    assertThat(actual.namedByContact()).isEmpty();

  }

  @Test
  void should_return_contacts_that_named_the_patient_as_a_contact() {

    ContactNamedByPatientFinder namedByPatientFinder = mock(ContactNamedByPatientFinder.class);

    when(namedByPatientFinder.find(anyLong())).thenReturn(List.of());

    PatientNamedByContactFinder namedByContactFinder = mock(PatientNamedByContactFinder.class);

    when(namedByContactFinder.find(anyLong())).thenReturn(
        List.of(
            new PatientContacts.NamedByContact(
                727L,
                Instant.parse("2023-04-12T18:01:00Z"),
                "condition-value",
                new PatientContacts.NamedContact(
                    2053L,
                    "contact-name-value"
                ),
                Instant.parse("2023-07-09T07:00:13Z"),
                "event-value",
                new PatientContacts.Investigation(
                    1033L,
                    "local-value",
                    "condition-value"
                )
            )
        )
    );

    PatientContactsByPatientResolver resolver = new PatientContactsByPatientResolver(
        namedByPatientFinder,
        namedByContactFinder
    );

    PatientContacts actual = resolver.find(811L);

    assertThat(actual)
        .returns(811L, PatientContacts::patient);

    assertThat(actual.namedByPatient()).isEmpty();

    assertThat(actual.namedByContact()).hasSize(1);

  }
}
