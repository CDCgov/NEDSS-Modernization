package gov.cdc.nbs.entity.odse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Alert {
    @Id
    @Column(name = "alert_uid", nullable = false)
    private Long id;

    @Column(name = "type_cd", nullable = false, length = 20)
    private String typeCd;

    @Column(name = "condition_cd", length = 20)
    private String conditionCd;

    @Column(name = "jurisdiction_cd", length = 20)
    private String jurisdictionCd;

    @Column(name = "add_time")
    private Instant addTime;

    @Column(name = "last_chg_time")
    private Instant lastChgTime;

    @Column(name = "record_status_cd", length = 20)
    private String recordStatusCd;

    @Column(name = "add_user_id")
    private Long addUserId;

    @Column(name = "last_chg_user_id")
    private Long lastChgUserId;

    @Column(name = "severity_cd", length = 20)
    private String severityCd;

    @Lob
    @Column(name = "alert_msg_txt")
    private String alertMsgTxt;

}
