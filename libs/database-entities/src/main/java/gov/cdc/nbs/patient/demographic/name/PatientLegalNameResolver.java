package gov.cdc.nbs.patient.demographic.name;

import gov.cdc.nbs.entity.odse.PersonName;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;

public class PatientLegalNameResolver {

  public static Optional<PersonName> resolve(
      final Collection<PersonName> names,
      final LocalDate asOf
  ) {
    return names.stream()
        .filter(
            PersonName.active()
                .and(PersonName.havingType("L"))
                .and(name -> name.asOf().isEqual(asOf) || name.asOf().isBefore(asOf))
        )
        .max(
            Comparator.comparing(PersonName::asOf)
            .thenComparing(name -> name.getId().getPersonNameSeq())
        );
  }

  private PatientLegalNameResolver() {

  }
}
