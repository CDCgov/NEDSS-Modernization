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
@Table(catalog = "NBS_SRTE", name = "Country_code")
public class CountryCode {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "code", nullable = false, length = 20)
  private String id;

  @Column(name = "code_desc_txt")
  private String codeDescTxt;

}
