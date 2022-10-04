package gov.cdc.nbs.entity.odse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.persistence.Entity;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "dsm_algorithm")
public class DsmAlgorithm {
    @Id
    @Column(name = "dsm_algorithm_uid", nullable = false)
    private Long id;

    @Column(name = "algorithm_nm", length = 250)
    private String algorithmNm;

    @Column(name = "event_type", length = 50)
    private String eventType;

    @Column(name = "condition_list", length = 250)
    private String conditionList;

    @Column(name = "frequency", length = 50)
    private String frequency;

    @Column(name = "apply_to", length = 50)
    private String applyTo;

    @Column(name = "sending_system_list", length = 250)
    private String sendingSystemList;

    @Column(name = "reporting_system_list", length = 250)
    private String reportingSystemList;

    @Column(name = "event_action", length = 50)
    private String eventAction;

    @Lob
    @Column(name = "algorithm_payload", columnDefinition = "TEXT")
    private String algorithmPayload;

    @Column(name = "admin_comment", length = 2000)
    private String adminComment;

    @Column(name = "status_cd", nullable = false, length = 50)
    private String statusCd;

    @Column(name = "status_time", nullable = false)
    private Instant statusTime;

    @Column(name = "last_chg_user_id", nullable = false)
    private Long lastChgUserId;

    @Column(name = "last_chg_time", nullable = false)
    private Instant lastChgTime;

    @Column(name = "resulted_test_list", length = 8000)
    private String resultedTestList;

}