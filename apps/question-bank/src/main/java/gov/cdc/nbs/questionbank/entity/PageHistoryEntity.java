package gov.cdc.nbs.questionbank.entity;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
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
@Table(name = "WA_template_hist", catalog = "NBS_ODSE")
public class PageHistoryEntity {
  @Id
  @Column(name = "wa_template_hist_uid", nullable = false)
  private Long id;

  @Column(name = "wa_template_uid", nullable = false)
  private Long waTemplateUid;

  @Column(name = "template_type", nullable = false, length = 50)
  private String templateType;

  @Lob
  @Column(name = "xml_payload", columnDefinition = "TEXT")
  private String xmlPayload;

  @Column(name = "publish_version_nbr")
  private Integer publishVersionNbr;

  @Column(name = "form_cd", length = 50)
  private String formCd;

  @Column(name = "condition_cd", length = 20)
  private String conditionCd;

  @Column(name = "bus_obj_type", nullable = false, length = 50)
  private String busObjType;

  @Column(name = "datamart_nm", length = 21)
  private String datamartNm;

  @Column(name = "record_status_cd", nullable = false, length = 20)
  private String recordStatusCd;

  @Column(name = "record_status_time", nullable = false)
  private Instant recordStatusTime;

  @Column(name = "last_chg_time", nullable = false)
  private Instant lastChgTime;

  @Column(name = "last_chg_user_id", nullable = false)
  private Long lastChgUserId;

  @Column(name = "local_id", length = 50)
  private String localId;

  @Column(name = "desc_txt", length = 2000)
  private String descTxt;

  @Column(name = "template_nm", length = 50)
  private String templateNm;

  @Column(name = "publish_ind_cd")
  private Character publishIndCd;

  @Column(name = "add_time", nullable = false)
  private Instant addTime;

  @Column(name = "add_user_id", nullable = false)
  private Long addUserId;

  @Column(name = "nnd_entity_identifier", length = 200)
  private String nndEntityIdentifier;

  @Column(name = "parent_template_uid")
  private Long parentTemplateUid;

  @Column(name = "source_nm", length = 250)
  private String sourceNm;

  @Column(name = "template_version_nbr")
  private Integer templateVersionNbr;

  @Column(name = "version_note", length = 2000)
  private String versionNote;
}
