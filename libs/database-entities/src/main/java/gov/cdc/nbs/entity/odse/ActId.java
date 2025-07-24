package gov.cdc.nbs.entity.odse;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "Act_id")
public class ActId {
  @EmbeddedId
  private ActIdId id;

  @MapsId("actUid")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "act_uid", nullable = false)
  private Act actUid;

  @Column(name = "add_reason_cd", length = 20)
  private String addReasonCd;

  @Column(name = "add_time")
  private Instant addTime;

  @Column(name = "add_user_id")
  private Long addUserId;

  @Column(name = "assigning_authority_cd", length = 199)
  private String assigningAuthorityCd;

  @Column(name = "assigning_authority_desc_txt", length = 100)
  private String assigningAuthorityDescTxt;

  @Column(name = "duration_amt", length = 20)
  private String durationAmt;

  @Column(name = "duration_unit_cd", length = 20)
  private String durationUnitCd;

  @Column(name = "last_chg_reason_cd", length = 20)
  private String lastChgReasonCd;

  @Column(name = "last_chg_time")
  private Instant lastChgTime;

  @Column(name = "last_chg_user_id")
  private Long lastChgUserId;

  @Column(name = "record_status_cd", length = 20)
  private String recordStatusCd;

  @Column(name = "record_status_time")
  private Instant recordStatusTime;

  @Column(name = "root_extension_txt", length = 199)
  private String rootExtensionTxt;

  @Column(name = "status_cd")
  private Character statusCd;

  @Column(name = "status_time")
  private Instant statusTime;

  @Column(name = "type_cd", length = 50)
  private String typeCd;

  @Column(name = "type_desc_txt", length = 100)
  private String typeDescTxt;

  @Column(name = "user_affiliation_txt", length = 20)
  private String userAffiliationTxt;

  @Column(name = "valid_from_time")
  private Instant validFromTime;

  @Column(name = "valid_to_time")
  private Instant validToTime;

  protected ActId() {

  }

}
