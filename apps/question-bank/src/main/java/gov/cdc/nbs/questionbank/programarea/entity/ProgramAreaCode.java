package gov.cdc.nbs.questionbank.programarea.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(catalog = "NBS_SRTE", name = "Program_area_code")
public class ProgramAreaCode implements Serializable {
  @Id
  @Column(name = "prog_area_cd", nullable = false, length = 20)
  private String id;

  @Column(name = "prog_area_desc_txt", length = 50)
  private String progAreaDescTxt;

  @Column(name = "nbs_uid")
  private Integer nbsUid;

  @Column(name = "status_cd")
  private Character statusCd;

  @Column(name = "status_time")
  private Instant statusTime;

  @Column(name = "code_set_nm", length = 256)
  private String codeSetNm;

  @Column(name = "code_seq")
  private Short codeSeq;
}
