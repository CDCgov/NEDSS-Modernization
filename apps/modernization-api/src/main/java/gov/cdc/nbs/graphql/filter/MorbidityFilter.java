package gov.cdc.nbs.graphql.filter;




import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MorbidityFilter {
	private Long patientId;
	private List<Long> jurisdictions;
	private String condition;
	private Long labReportId;

}
