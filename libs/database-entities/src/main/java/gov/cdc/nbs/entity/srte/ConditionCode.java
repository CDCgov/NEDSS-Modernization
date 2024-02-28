package gov.cdc.nbs.entity.srte;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Getter
@Entity
@Table(catalog = "NBS_SRTE", name = "Condition_code")
public class ConditionCode implements Serializable {
  @Id
  @Column(name = "condition_cd", nullable = false, length = 20)
  private String id;

  @Column(name = "condition_desc_txt", length = 300)
  private String conditionDescTxt;

  @Column(name = "condition_short_nm", length = 50)
  private String conditionShortNm;

  @Column(name = "investigation_form_cd", length = 50)
  private String investigationFormCd;

}
