package gov.cdc.nbs.entity.srte;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(catalog = "NBS_SRTE", name = "Labtest_loinc")
public class LabtestLoinc {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EmbeddedId
    private LabtestLoincId id;

    @MapsId
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumns({
            @JoinColumn(name = "lab_test_cd", referencedColumnName = "lab_test_cd", nullable = false),
            @JoinColumn(name = "laboratory_id", referencedColumnName = "laboratory_id", nullable = false)
    })
    private LabTest labTest;

    @MapsId("loincCd")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "loinc_cd", nullable = false)
    private LoincCode loincCd;

    @Column(name = "effective_from_time")
    private Instant effectiveFromTime;

    @Column(name = "effective_to_time")
    private Instant effectiveToTime;

    @Column(name = "status_cd")
    private Character statusCd;

    @Column(name = "status_time")
    private Instant statusTime;

}