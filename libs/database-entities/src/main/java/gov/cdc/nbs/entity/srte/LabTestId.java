package gov.cdc.nbs.entity.srte;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serial;
import java.io.Serializable;

@Getter
@Embeddable
public class LabTestId implements Serializable {
  @Serial
  private static final long serialVersionUID = -8895203828822190824L;
  @Column(name = "lab_test_cd", nullable = false, length = 20)
  private String labTestCd;

  @Column(name = "laboratory_id", nullable = false, length = 20)
  private String laboratoryId;

  public LabTestId() {
  }

  public LabTestId(final String labTestCd, final String laboratoryId) {
    this();
    this.labTestCd = labTestCd;
    this.laboratoryId = laboratoryId;
  }

}
