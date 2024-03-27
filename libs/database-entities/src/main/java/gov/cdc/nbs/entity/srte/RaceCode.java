package gov.cdc.nbs.entity.srte;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(catalog = "NBS_SRTE", name = "Race_code")
public class RaceCode {
  @Id
  @Column(name = "code", nullable = false, length = 20)
  private String id;

  @Column(name = "code_short_desc_txt", length = 50)
  private String codeShortDescTxt;

  @Column(name = "indent_level_nbr")
  private Short indentLevelNbr;

  @Column(name = "parent_is_cd", length = 20)
  private String parentIsCd;

}
