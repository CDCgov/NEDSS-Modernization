package gov.cdc.nbs.patientlistener.message;

import java.util.List;

import gov.cdc.nbs.patientlistener.odse.Person;
import gov.cdc.nbs.patientlistener.odse.PostalLocator;
import gov.cdc.nbs.patientlistener.odse.TeleLocator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PatientUpdateParams {
	private Person updatePerson;
	private List<TemplateInput> templateInputs;
	private List<TeleLocator> teleLocators;
	private List<PostalLocator> postalLocators;

}
