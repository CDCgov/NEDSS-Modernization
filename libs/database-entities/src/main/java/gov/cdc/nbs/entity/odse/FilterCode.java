package gov.cdc.nbs.entity.odse;

import gov.cdc.nbs.audit.Status;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "Filter_code", catalog = "NBS_ODSE")
public class FilterCode {
  @Id
  @Column(name = "filter_uid", nullable = false)
  private Long id;

  @NonNull @Column(name = "code_table", length = 50)
  private String codeTable;

  @Column(name = "desc_txt", length = 300)
  private String descTxt;

  @Column(name = "effective_from_time")
  private LocalDateTime effectiveFromTime;

  @Column(name = "effective_to_time")
  private LocalDateTime effectiveToTime;

  @Column(name = "filter_code", length = 20)
  private String code;

  @Column(name = "filter_code_set_nm", length = 256)
  private String filterCodeSetName;

  @Column(name = "filter_type", length = 20)
  private String filterType;

  @Column(name = "filter_name", length = 50)
  private String filterName;

  @Embedded private Status status;

  protected FilterCode() {}
}
