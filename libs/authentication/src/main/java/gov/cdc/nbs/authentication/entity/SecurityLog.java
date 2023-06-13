package gov.cdc.nbs.authentication.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import gov.cdc.nbs.authentication.enums.SecurityEventType;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "event_type_cd", length = 20)
    private SecurityEventType eventTypeCd;

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
