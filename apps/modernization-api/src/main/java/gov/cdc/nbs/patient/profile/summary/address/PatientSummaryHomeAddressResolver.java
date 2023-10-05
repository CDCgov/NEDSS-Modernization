package gov.cdc.nbs.patient.profile.summary.address;

import gov.cdc.nbs.address.Address;
import gov.cdc.nbs.patient.profile.summary.PatientSummary;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
class PatientSummaryHomeAddressResolver {

  private final PatientSummaryHomeAddressFinder finder;


  PatientSummaryHomeAddressResolver(final PatientSummaryHomeAddressFinder finder) {
    this.finder = finder;
  }

  @SchemaMapping(typeName = "PatientSummary", field = "home")
  Optional<Address> resolve(final PatientSummary summary) {
    return this.finder.find(summary.patient(), summary.asOf());
  }

}
