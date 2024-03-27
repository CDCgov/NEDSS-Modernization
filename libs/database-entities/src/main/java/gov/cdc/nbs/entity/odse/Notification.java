package gov.cdc.nbs.entity.odse;

import gov.cdc.nbs.audit.Audit;
import gov.cdc.nbs.audit.Status;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import java.time.Instant;

@Getter
@Setter
@Entity
public class Notification {
  @Id
  @Column(name = "notification_uid", nullable = false)
  private Long id;

  @MapsId
  @OneToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "notification_uid", nullable = false)
  private Act act;

  @Column(name = "local_id", length = 50)
  private String localId;

  @Column(name = "shared_ind", nullable = false)
  private Character sharedInd;

  @Column(name = "version_ctrl_nbr", nullable = false)
  private Short versionCtrlNbr;

  @Embedded
  private Audit audit;

  @Embedded
  private Status status;

  @Column(name = "record_status_cd", length = 20)
  private String recordStatusCd;

  @Column(name = "record_status_time")
  private Instant recordStatusTime;

  protected Notification() {
    this.sharedInd = 'F';
    this.versionCtrlNbr = 1;
  }

}
