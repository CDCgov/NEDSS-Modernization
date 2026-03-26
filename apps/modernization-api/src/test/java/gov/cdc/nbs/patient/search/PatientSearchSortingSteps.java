package gov.cdc.nbs.patient.search;

import gov.cdc.nbs.search.support.SortCriteria;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;
import org.springframework.data.domain.Sort;

public class PatientSearchSortingSteps {

  private final Active<SortCriteria> sorting;

  PatientSearchSortingSteps(final Active<SortCriteria> sorting) {
    this.sorting = sorting;
  }

  @Given("I want patients sorted by {string} {string}")
  public void i_want_patients_sorted_by(final String sortBy, final String direction) {
    Sort.Direction sortDirection =
        direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;

    String field =
        switch (sortBy.toLowerCase()) {
          case "local_id", "patient id" -> "patientid";
          case "birthday" -> "birthTime";
          case "first name" -> "firstNm";
          case "last name" -> "lastNm";
          case "legal name" -> "legalName";
          case "phone" -> "phoneNumber";
          default -> sortBy.toLowerCase();
        };

    this.sorting.active(new SortCriteria(sortDirection, field));
  }
}
