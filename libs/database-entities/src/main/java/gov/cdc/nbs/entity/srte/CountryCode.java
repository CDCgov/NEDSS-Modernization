package gov.cdc.nbs.entity.srte;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(catalog = "NBS_SRTE", name = "Country_code")
public class CountryCode {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "code", nullable = false, length = 20)
  private String id;

  @Column(name = "code_desc_txt")
  private String codeDescTxt;

}
