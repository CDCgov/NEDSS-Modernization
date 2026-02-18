package gov.cdc.nbs.questionbank.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
@EqualsAndHashCode
public class CodesetId implements Serializable {

  private static final long serialVersionUID = 1L;

  @Column(name = "class_cd", nullable = false, length = 30)
  private String classCd;

  @Column(name = "code_set_nm", nullable = false, length = 256)
  private String codeSetNm;
}
