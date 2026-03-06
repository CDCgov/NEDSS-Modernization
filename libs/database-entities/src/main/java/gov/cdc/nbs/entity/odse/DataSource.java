package gov.cdc.nbs.entity.odse;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.*;

@AllArgsConstructor
@RequiredArgsConstructor
@Builder
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

  //  TODO: add a converter? // NOSONAR
  @Column(name = "condition_security", length = 1)
  private Character conditionSecurity;

  //  TODO: add a converter? // NOSONAR
  @Column(name = "jurisdiction_security", length = 1)
  private Character jurisdictionSecurity;

  //  TODO: add a converter? // NOSONAR
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

  //  TODO: add a converter? Or Separate Status class? // NOSONAR
  @NonNull @Column(name = "status_cd", nullable = false)
  private Character statusCd;

  @Column(name = "status_time")
  private LocalDateTime statusTime;

  protected DataSource() {}
}
