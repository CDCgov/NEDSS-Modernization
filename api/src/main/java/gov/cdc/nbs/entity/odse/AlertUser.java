package gov.cdc.nbs.entity.odse;

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
@Table(name = "Alert_User")
public class AlertUser {
    @Id
    @Column(name = "alert_user_uid", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "alert_uid", nullable = false)
    private Alert alertUid;

    @Column(name = "nedss_entry_id", nullable = false)
    private Long nedssEntryId;

}