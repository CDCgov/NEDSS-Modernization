package gov.cdc.nbs.entity.srte;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(catalog = "NBS_SRTE", name = "Lab_result_Snomed")
public class LabResultSnomed implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EmbeddedId
    private LabResultSnomedId id;

    @MapsId
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "lab_result_cd", referencedColumnName = "lab_result_cd", nullable = false)
    @JoinColumn(name = "laboratory_id", referencedColumnName = "laboratory_id", nullable = false)
    private LabResult labResult;

    @MapsId("snomedCd")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "snomed_cd", nullable = false)
    private SnomedCode snomedCd;

    @Column(name = "effective_from_time")
    private Instant effectiveFromTime;

    @Column(name = "effective_to_time")
    private Instant effectiveToTime;

    @Column(name = "status_cd")
    private Character statusCd;

    @Column(name = "status_time")
    private Instant statusTime;

}
