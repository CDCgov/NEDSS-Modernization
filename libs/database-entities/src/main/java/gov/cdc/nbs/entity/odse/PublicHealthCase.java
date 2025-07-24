package gov.cdc.nbs.entity.odse;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Public_health_case")
public class PublicHealthCase {

  @Column(name = "activity_from_time")
  private Instant activityFromTime;

  @Column(name = "activity_to_time")
  private Instant activityToTime;

  @Column(name = "add_time")
  private Instant addTime;

  @Column(name = "add_user_id")
  private Long addUserId;

  @Column(name = "case_class_cd", length = 20)
  private String caseClassCd;

  @Column(name = "case_type_cd")
  private Character caseTypeCd;

  @Column(name = "cd", length = 50)
  private String cd;

  @Column(name = "cd_desc_txt", length = 100)
  private String cdDescTxt;

  @Column(name = "investigation_status_cd", length = 20)
  private String investigationStatusCd;

  @Column(name = "jurisdiction_cd", length = 20)
  private String jurisdictionCd;

  @Column(name = "last_chg_reason_cd", length = 20)
  private String lastChgReasonCd;

  @Column(name = "last_chg_time")
  private Instant lastChgTime;

  @Column(name = "last_chg_user_id")
  private Long lastChgUserId;

  @Column(name = "local_id", length = 50)
  private String localId;

  @Column(name = "outbreak_name", length = 100)
  private String outbreakName;

  @Column(name = "prog_area_cd", length = 20)
  private String progAreaCd;

  @Column(name = "record_status_cd", length = 20)
  private String recordStatusCd;

  @Column(name = "record_status_time")
  private Instant recordStatusTime;

  @Column(name = "rpt_form_cmplt_time")
  private Instant rptFormCmpltTime;

  @Column(name = "status_cd")
  private Character statusCd;

  @Column(name = "status_time")
  private Instant statusTime;

  @Column(name = "program_jurisdiction_oid")
  private long programJurisdictionOid;

  @Column(name = "shared_ind", nullable = false)
  private Character sharedInd;

  @Column(name = "version_ctrl_nbr", nullable = false)
  private Short versionCtrlNbr;

  @Column(name = "pregnant_ind_cd", length = 20)
  private String pregnantIndCd;

  @Column(name = "curr_process_state_cd", length = 20)
  private String currProcessStateCd;

  @Id
  @Column(name = "public_health_case_uid", nullable = false)
  private Long id;

  @Column(name = "coinfection_id", length = 50)
  private String coinfectionId;

  @MapsId
  @OneToOne(fetch = FetchType.LAZY, optional = false, cascade = {
      CascadeType.PERSIST,
      CascadeType.MERGE,
      CascadeType.REMOVE
  }, orphanRemoval = true)
  @JoinColumn(name = "public_health_case_uid", nullable = false)
  private Act act;

  @OneToMany(mappedBy = "subjectEntityPhcUid", fetch = FetchType.LAZY, cascade = {
      CascadeType.PERSIST,
      CascadeType.MERGE,
      CascadeType.REMOVE
  }, orphanRemoval = true)
  private List<CtContact> subjectContacts;

  protected PublicHealthCase() {
    // no args constructor required for JPA
  }

  public void addSubjectContact(final CtContact contact) {
    this.ensureSubjectContacts().add(contact);
  }

  private List<CtContact> ensureSubjectContacts() {
    if (this.subjectContacts == null) {
      this.subjectContacts = new ArrayList<>();
    }

    return this.subjectContacts;
  }

}
