package gov.cdc.nbs.entity.odse;

import gov.cdc.nbs.audit.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "Data_source_column", catalog = "NBS_ODSE")
public class DataSourceColumn {

  @Id
  @Column(name = "column_uid", nullable = false)
  private Long id;

  @Column(name = "column_max_length")
  private Integer columnMaxLength;

  @Column(name = "column_name", length = 50)
  private String columnName;

  @Column(name = "column_title", length = 50)
  private String columnTitle;

  @Column(name = "column_type_code", length = 20)
  private String columnSourceTypeCode;

  @Column(name = "desc_txt", length = 300)
  private String descTxt;

  //  TODO: add a converter?
  @Column(name = "displayable", length = 1)
  private Character displayable;

  @Column(name = "effective_from_time")
  private LocalDate effectiveFromTime;

  @Column(name = "effective_to_time")
  private LocalDate effectiveToTime;

  //  TODO: add a converter?
  @Column(name = "filterable", length = 1)
  private Character filterable;

  @Embedded
  private Status status;
}
