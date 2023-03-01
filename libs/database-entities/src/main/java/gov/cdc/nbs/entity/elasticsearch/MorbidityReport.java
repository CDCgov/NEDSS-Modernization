package gov.cdc.nbs.entity.elasticsearch;



import java.time.Instant;

import javax.persistence.Id;

import org.springframework.data.elasticsearch.annotations.Document;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(indexName = "morbidity_report")
public class MorbidityReport {
	
	@Id
    private String id;
	
	private Long reportUid;
	
	private Long dataSourceUid;
	
	private String addReasonCd;
	
	private Instant addTime;
	
	private String descTxt;
	
	private Instant effectiveFromTime;
	
	private Instant effectiveToTime;
	
	private char filterMode;
	
	private char isModifiableInd;
	
	private String location;
	
	private Long ownerUid;
	
	private String orgAccessPermis;
	
	private String progAreaAccessPermis;
	
	private String reportTitle;
	
	private String reportTypeCode;
	
	private char shared;
	
	private char statusCd;
	
	private Instant statusTime;
	
	private String category;
	
	private String sectionCd;
	
	

}
