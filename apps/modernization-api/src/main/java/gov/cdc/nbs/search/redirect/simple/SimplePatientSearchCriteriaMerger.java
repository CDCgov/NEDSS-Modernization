package gov.cdc.nbs.search.redirect.simple;

import gov.cdc.nbs.option.Option;

import java.time.LocalDate;

class SimplePatientSearchCriteriaMerger {

  static SimplePatientSearchCriteria merge(final SimplePatientSearchCriteria left,
      final SimplePatientSearchCriteria right) {

    String first = left.firstName() == null ? right.firstName() : left.firstName();
    String last = left.lastName() == null ? right.lastName() : left.lastName();
    String id = left.id() == null ? right.id() : left.id();
    Option gender = left.gender() == null ? right.gender() : left.gender();
    LocalDate dateOfBirth = left.dateOfBirth() == null ? right.dateOfBirth() : left.dateOfBirth();

    return new SimplePatientSearchCriteria(
        last,
        first,
        dateOfBirth,
        gender,
        id
    );
  }

  private SimplePatientSearchCriteriaMerger(){}

}
