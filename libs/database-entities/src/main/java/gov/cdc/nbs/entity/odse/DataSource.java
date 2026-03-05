package gov.cdc.nbs.entity.odse;

import gov.cdc.nbs.audit.Status;
import jakarta.persistence.*;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Data_Source", catalog = "NBS_ODSE")
public class DataSource {

    @Id
    @Column(name = "data_source_uid", nullable = false, updatable = false)
    private Long id;

    @Column(name = "column_max_le")
    private Integer columnMaxLen;

    //  TODO: add a converter?
    @Column(name = "condition_security", length = 1)
    private Character conditionSecurity;

    //  TODO: add a converter?
    @Column(name = "jurisdiction_security", length = 1)
    private Character jurisdictionSecurity;

    //  TODO: add a converter?
    @Column(name = "reporting_facility_security", length = 1)
    private Character reportingFacilitySecurity;

    @Column(name = "data_source_name", length = 50)
    private String dataSourceName;

    @Column(name = "data_source_title", length = 50)
    private String dataSourceTitle;

    @Column(name = "data_source_type_code", length = 20)
    private String dataSourceTypeCode;

    @Column(name = "desc_txt", length = 300)
    private String descTxt;

    @Column(name = "effective_from_time")
    private LocalDate effectiveFromTime;

    @Column(name = "effective_to_time")
    private LocalDate effectiveToTime;

    @Column(name = "org_access_permis", length = 2000)
    private String orgAccessPermission;

    @Column(name = "prog_area_access_permis", length = 2000)
    private String progAreaAccessPermission;

    @Embedded
    private Status status;
}
