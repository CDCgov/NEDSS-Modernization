package gov.cdc.nbs.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "message_log")
public class MessageLog {
    @Id
    @Column(name = "message_log_uid", nullable = false)
    private Long id;

    @Column(name = "message_txt", nullable = false, length = 2000)
    private String messageTxt;

    @Column(name = "condition_cd", length = 50)
    private String conditionCd;

    @Column(name = "person_uid")
    private Long personUid;

    @Column(name = "assigned_to_uid")
    private Long assignedToUid;

    @Column(name = "event_uid")
    private Long eventUid;

    @Column(name = "event_type_cd", length = 20)
    private String eventTypeCd;

    @Column(name = "message_status_cd", length = 20)
    private String messageStatusCd;

    @Column(name = "record_status_cd", nullable = false, length = 20)
    private String recordStatusCd;

    @Column(name = "record_status_time", nullable = false)
    private Instant recordStatusTime;

    @Column(name = "add_time", nullable = false)
    private Instant addTime;

    @Column(name = "add_user_id", nullable = false)
    private Long addUserId;

    @Column(name = "last_chg_time", nullable = false)
    private Instant lastChgTime;

    @Column(name = "last_chg_user_id", nullable = false)
    private Long lastChgUserId;

}