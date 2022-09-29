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
@Table(name = "Security_log")
public class SecurityLog {
    @Id
    @Column(name = "security_log_uid", nullable = false)
    private Long id;

    @Column(name = "user_id", length = 50)
    private String userId;

    @Column(name = "event_type_cd", length = 20)
    private String eventTypeCd;

    @Column(name = "event_time")
    private Instant eventTime;

    @Column(name = "session_id", length = 500)
    private String sessionId;

    @Column(name = "user_ip_addr", length = 500)
    private String userIpAddr;

    @Column(name = "nedss_entry_id")
    private Long nedssEntryId;

    @Column(name = "first_nm", length = 50)
    private String firstNm;

    @Column(name = "last_nm", length = 50)
    private String lastNm;

}