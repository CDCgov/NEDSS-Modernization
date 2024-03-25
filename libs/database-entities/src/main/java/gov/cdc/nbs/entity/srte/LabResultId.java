package gov.cdc.nbs.entity.srte;

import lombok.Getter;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serial;
import java.io.Serializable;

@Getter
@Embeddable
public class LabResultId implements Serializable {
  @Serial
  private static final long serialVersionUID = 8829918096085151951L;
  @Column(name = "lab_result_cd", nullable = false, length = 20)
  private String labResultCd;

  @Column(name = "laboratory_id", nullable = false, length = 20)
  private String laboratoryId;

  public LabResultId() {
  }

  public LabResultId(final String labResultCd, final String laboratoryId) {
    this();
    this.labResultCd = labResultCd;
    this.laboratoryId = laboratoryId;
  }

}
