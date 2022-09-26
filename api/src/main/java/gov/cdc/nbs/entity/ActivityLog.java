package gov.cdc.nbs.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Activity_log")
public class ActivityLog {
    @Id
    @Column(name = "activity_log_uid", nullable = false)
    private Long id;

    @Column(name = "doc_type", length = 50)
    private String docType;

    @Column(name = "doc_nm", length = 250)
    private String docNm;

    @Column(name = "record_status_cd", length = 20)
    private String recordStatusCd;

    @Column(name = "record_status_time")
    private Instant recordStatusTime;

    @Lob
    @Column(name = "message_txt")
    private String messageTxt;

    @Column(name = "action_txt", length = 50)
    private String actionTxt;

    @Column(name = "source_type_cd", length = 50)
    private String sourceTypeCd;

    @Column(name = "target_type_cd", length = 50)
    private String targetTypeCd;

    @Column(name = "source_Id", length = 50)
    private String sourceId;

    @Column(name = "target_Id", length = 50)
    private String targetId;

    @Column(name = "add_user_id", nullable = false)
    private Long addUserId;

    @Column(name = "add_time", nullable = false)
    private Instant addTime;

}