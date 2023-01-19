package gov.cdc.nbs.message;

import java.util.List;

import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.entity.odse.PostalLocator;
import gov.cdc.nbs.entity.odse.TeleLocator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PatientUpdateParams {
	private Person updatePerson;
	private List<TemplateInput> templateInputs;
	private List<TeleLocator> teleLocators;
	private List<PostalLocator> postalLocators;

}
