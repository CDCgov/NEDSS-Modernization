package gov.cdc.nbs.entity.srte;

import lombok.Getter;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

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

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    LabTestId labTestId = (LabTestId) o;
    return Objects.equals(labTestCd, labTestId.labTestCd) && Objects.equals(laboratoryId,
        labTestId.laboratoryId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(labTestCd, laboratoryId);
  }
}
