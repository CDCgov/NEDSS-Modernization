package gov.cdc.nbs.entity.srte;

import lombok.Getter;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serial;
import java.io.Serializable;

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

}
