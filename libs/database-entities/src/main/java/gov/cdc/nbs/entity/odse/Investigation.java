package gov.cdc.nbs.entity.odse;

import javax.persistence.Column;
import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.Instant;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Investigation {
    @Column(name = "public_health_case_uid", nullable = false)
    private Long id;

    @Column(name = "last_chg_time")
    private Instant lastChgTime;

    @Column(name = "cd_desc_txt", length = 100)
    private String cdDescTxt;

    @Column(name = "local_id", length = 50)
    private String localId;

    @Column(name = "act_relationship_last_change_time")
    private Instant actRelationshipLastChgTime;
}
