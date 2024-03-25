package gov.cdc.nbs.entity.srte;

import lombok.Getter;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Embeddable
public class CodeValueGeneralId implements Serializable {
  @Serial
  private static final long serialVersionUID = -1050691182408565760L;
  @Column(name = "code_set_nm", nullable = false, length = 256)
  private String codeSetNm;

  @Column(name = "code", nullable = false, length = 20)
  private String code;

  public CodeValueGeneralId() {
  }

  public CodeValueGeneralId(String codeSetNm, String code) {
    this.codeSetNm = codeSetNm;
    this.code = code;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    CodeValueGeneralId that = (CodeValueGeneralId) o;
    return Objects.equals(codeSetNm, that.codeSetNm) && Objects.equals(code, that.code);
  }

  @Override
  public int hashCode() {
    return Objects.hash(codeSetNm, code);
  }
}
