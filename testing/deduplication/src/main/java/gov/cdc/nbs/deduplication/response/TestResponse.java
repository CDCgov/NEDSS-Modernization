package gov.cdc.nbs.deduplication.response;

import java.util.ArrayList;
import java.util.List;
import gov.cdc.nbs.deduplication.model.PatientData;

public record TestResponse(
    boolean patientIngested,
    List<String> exactMatches,
    List<String> similarMatches,
    PatientData data) {
  public TestResponse(boolean patientIngested, PatientData data) {
    this(patientIngested, new ArrayList<>(), new ArrayList<>(), data);
  }
}
