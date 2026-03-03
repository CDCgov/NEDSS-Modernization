package gov.cdc.nbs.entity.odse;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

  @ManyToOne(fetch = FetchType.LAZY) // TODO: leave as-is or default to EAGER?
  @JoinColumn(name = "data_source_uid")
  private DataSource dataSourceUid;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ReportId reportId)) return false;
    return Objects.equals(reportUid, reportId.reportUid)
        && Objects.equals(dataSourceUid, reportId.dataSourceUid);
  }

  @Override
  public int hashCode() {
    return Objects.hash(reportUid, dataSourceUid);
  }
}
