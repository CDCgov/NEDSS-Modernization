package gov.cdc.nbs.entity.srte;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(catalog = "NBS_SRTE", name = "State_county_code_value")
public class StateCountyCodeValue {
  @Id
  @Column(name = "code", nullable = false, length = 20)
  private String id;

  @Column(name = "code_desc_txt")
  private String codeDescTxt;

  @Column(name = "Indent_level_nbr")
  private Short indentLevelNbr;


  @Column(name = "parent_is_cd", length = 20)
  private String parentIsCd;



}
