package gov.cdc.nbs.entity.srte;

import lombok.Getter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

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
