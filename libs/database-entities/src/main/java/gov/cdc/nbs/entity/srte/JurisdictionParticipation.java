package gov.cdc.nbs.entity.srte;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(catalog = "NBS_SRTE", name = "Jurisdiction_participation")
public class JurisdictionParticipation {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EmbeddedId
    private JurisdictionParticipationId id;

    @MapsId("jurisdictionCd")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "jurisdiction_cd", nullable = false)
    private JurisdictionCode jurisdictionCd;

    @Column(name = "effective_from_time")
    private Instant effectiveFromTime;

    @Column(name = "effective_to_time")
    private Instant effectiveToTime;

}
