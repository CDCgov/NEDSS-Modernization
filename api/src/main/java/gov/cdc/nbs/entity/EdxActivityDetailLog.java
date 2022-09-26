package gov.cdc.nbs.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "EDX_activity_detail_log")
public class EdxActivityDetailLog {
    @Id
    @Column(name = "edx_activity_detail_log_uid", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "edx_activity_log_uid", nullable = false)
    private EdxActivityLog edxActivityLogUid;

    @Column(name = "record_id", length = 256)
    private String recordId;

    @Column(name = "record_type", length = 50)
    private String recordType;

    @Column(name = "record_nm", length = 250)
    private String recordNm;

    @Column(name = "log_type", length = 50)
    private String logType;

    @Column(name = "log_comment", length = 2000)
    private String logComment;

}