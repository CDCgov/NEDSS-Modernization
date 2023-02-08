package gov.cdc.nbs.entity.odse;

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
@Table(name = "User_Email")
public class UserEmail {
    @Id
    @Column(name = "user_email_uid", nullable = false)
    private Long id;

    @Column(name = "nedss_entry_id", nullable = false)
    private Long nedssEntryId;

    @Column(name = "seq_nbr", nullable = false)
    private Integer seqNbr;

    @Column(name = "email_id", length = 100)
    private String emailId;

    @Column(name = "add_time")
    private Instant addTime;

    @Column(name = "last_chg_time")
    private Instant lastChgTime;

    @Column(name = "record_status_cd", length = 8)
    private String recordStatusCd;

    @Column(name = "add_user_id")
    private Long addUserId;

    @Column(name = "last_chg_user_id")
    private Long lastChgUserId;

}