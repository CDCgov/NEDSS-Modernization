package gov.cdc.nbs.message;

import java.util.List;


import gov.cdc.nbs.graphql.input.PatientInput;
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
	private Long personId;
	private PatientInput input;
	private List<TemplateInput> templateInputs;

}
