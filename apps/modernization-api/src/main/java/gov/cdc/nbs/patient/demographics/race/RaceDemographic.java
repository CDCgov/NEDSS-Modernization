package gov.cdc.nbs.patient.demographics.race;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import gov.cdc.nbs.time.json.FormattedLocalDateJsonDeserializer;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public record RaceDemographic(
    @JsonDeserialize(using = FormattedLocalDateJsonDeserializer.class) LocalDate asOf,
    String race,
    List<String> detailed) {

  public RaceDemographic {
    detailed = detailed == null ? Collections.emptyList() : List.copyOf(detailed);
  }

  public RaceDemographic(final LocalDate asOf, final String race) {
    this(asOf, race, Collections.emptyList());
  }

  public RaceDemographic withAsOf(final LocalDate asOf) {
    return new RaceDemographic(asOf, race, detailed);
  }

  public RaceDemographic withRace(final String race) {
    return new RaceDemographic(asOf, race, Collections.emptyList());
  }

  public RaceDemographic withDetail(final String detail) {
    List<String> including = new ArrayList<>(detailed);
    including.add(detail);
    return new RaceDemographic(asOf(), race(), including);
  }
}
