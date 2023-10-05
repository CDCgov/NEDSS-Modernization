package gov.cdc.nbs.patient.profile.summary.address;

import gov.cdc.nbs.address.Address;
import gov.cdc.nbs.patient.profile.summary.PatientSummary;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.Collection;

@Controller
class PatientSummaryAddressResolver {

  private final PatientSummaryAddressFinder finder;


  PatientSummaryAddressResolver(final PatientSummaryAddressFinder finder) {
    this.finder = finder;
  }

  @SchemaMapping(typeName = "PatientSummary", field = "address")
  Collection<Address> resolve(final PatientSummary summary) {
    return this.finder.find(summary.patient());
  }

}
