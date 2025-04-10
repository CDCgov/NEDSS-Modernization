package gov.cdc.nbs.entity.srte;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Getter
@Setter
@Entity
@Table(catalog = "NBS_SRTE", name = "State_code")
public class StateCode {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "state_cd", nullable = false, length = 20)
  private String id;

  @Column(name = "state_nm", length = 2)
  private String stateNm;

  @Column(name = "code_desc_txt", length = 50)
  private String codeDescTxt;

}
