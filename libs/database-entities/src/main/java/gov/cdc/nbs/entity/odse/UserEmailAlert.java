package gov.cdc.nbs.entity.odse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import jakarta.persistence.Entity;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "User_Email_Alert")
public class UserEmailAlert {
    @Id
    @Column(name = "user_email_alert_uid", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "alert_email_message_uid", nullable = false)
    private AlertEmailMessage alertEmailMessageUid;

    @Column(name = "nedss_entry_uid", nullable = false)
    private Long nedssEntryUid;

    @Column(name = "email_id", length = 100)
    private String emailId;

    @Column(name = "seq_nbr")
    private Integer seqNbr;

}
