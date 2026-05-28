package gov.cdc.nbs.entity.odse;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class ReportId implements Serializable {

  @Column(name = "report_uid", nullable = false)
  private Long reportUid;

  @Column(name = "data_source_uid", nullable = false)
  private Long dataSourceUid;

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof ReportId reportId)) return false;
    return Objects.equals(reportUid, reportId.reportUid)
        && Objects.equals(dataSourceUid, reportId.dataSourceUid);
  }

  @Override
  public int hashCode() {
    return Objects.hash(reportUid, dataSourceUid);
  }
}
