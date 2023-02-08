package gov.cdc.nbs.entity.odse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.persistence.Entity;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Treatment_procedure_hist")
public class TreatmentProcedureHist {
    @EmbeddedId
    private TreatmentProcedureHistId id;

    @MapsId
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "treatment_uid", referencedColumnName = "treatment_uid", nullable = false)
    @JoinColumn(name = "treatment_procedure_seq", referencedColumnName = "treatment_procedure_seq", nullable = false)
    private TreatmentProcedure treatmentProcedure;

    @Column(name = "approach_site_cd", length = 20)
    private String approachSiteCd;

    @Column(name = "approach_site_desc_txt", length = 100)
    private String approachSiteDescTxt;

    @Column(name = "cd", length = 50)
    private String cd;

    @Column(name = "cd_desc_txt", length = 100)
    private String cdDescTxt;

    @Column(name = "cd_system_cd", length = 20)
    private String cdSystemCd;

    @Column(name = "cd_system_desc_txt", length = 100)
    private String cdSystemDescTxt;

    @Column(name = "cd_version", length = 10)
    private String cdVersion;

    @Column(name = "effective_duration_amt", length = 10)
    private String effectiveDurationAmt;

    @Column(name = "effective_duration_unit_cd", length = 20)
    private String effectiveDurationUnitCd;

    @Column(name = "effective_from_time")
    private Instant effectiveFromTime;

    @Column(name = "effective_to_time")
    private Instant effectiveToTime;

    @Column(name = "status_cd")
    private Character statusCd;

    @Column(name = "status_time")
    private Instant statusTime;

}