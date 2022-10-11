package gov.cdc.nbs.entity.odse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Data_Source")
public class DataSource {
    @Id
    @Column(name = "data_source_uid", nullable = false)
    private Long id;

    @Column(name = "column_max_len")
    private Short columnMaxLen;

    @Column(name = "condition_security")
    private Character conditionSecurity;

    @Column(name = "data_source_name", length = 50)
    private String dataSourceName;

    @Column(name = "data_source_title", length = 50)
    private String dataSourceTitle;

    @Column(name = "data_source_type_code", length = 20)
    private String dataSourceTypeCode;

    @Column(name = "desc_txt", length = 300)
    private String descTxt;

    @Column(name = "effective_from_time")
    private Instant effectiveFromTime;

    @Column(name = "effective_to_time")
    private Instant effectiveToTime;

    @Column(name = "jurisdiction_security")
    private Character jurisdictionSecurity;

    @Column(name = "org_access_permis", length = 2000)
    private String orgAccessPermis;

    @Column(name = "prog_area_access_permis", length = 2000)
    private String progAreaAccessPermis;

    @Column(name = "status_cd", nullable = false)
    private Character statusCd;

    @Column(name = "status_time")
    private Instant statusTime;

    @Column(name = "reporting_facility_security")
    private Character reportingFacilitySecurity;

}