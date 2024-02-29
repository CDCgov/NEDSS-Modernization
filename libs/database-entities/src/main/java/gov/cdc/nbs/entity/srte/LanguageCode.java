package gov.cdc.nbs.entity.srte;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Entity
@Table(catalog = "NBS_SRTE", name = "Language_code")
public class LanguageCode {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "code", nullable = false, length = 20)
  private String id;

  @Column(name = "code_short_desc_txt", length = 50)
  private String codeShortDescTxt;

  @Column(name = "indent_level_nbr")
  private Short indentLevelNbr;

}
