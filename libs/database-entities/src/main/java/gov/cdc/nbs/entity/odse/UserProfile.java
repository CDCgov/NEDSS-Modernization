package gov.cdc.nbs.entity.odse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "USER_PROFILE")
public class UserProfile {
    @Id
    @Column(name = "NEDSS_ENTRY_ID", nullable = false)
    private Long id;

    @Column(name = "FIRST_NM", length = 50)
    private String firstNm;

    @Column(name = "LAST_UPD_TIME")
    private Instant lastUpdTime;

    @Column(name = "LAST_NM", length = 50)
    private String lastNm;

}
