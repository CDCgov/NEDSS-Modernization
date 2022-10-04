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
@Table(name = "Treatment_administered")
public class TreatmentAdministered {
    @EmbeddedId
    private TreatmentAdministeredId id;

    @MapsId("treatmentUid")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "treatment_uid", nullable = false)
    private Treatment treatmentUid;

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

    @Column(name = "dose_qty", length = 20)
    private String doseQty;

    @Column(name = "dose_qty_unit_cd", length = 20)
    private String doseQtyUnitCd;

    @Column(name = "effective_duration_amt", length = 10)
    private String effectiveDurationAmt;

    @Column(name = "effective_duration_unit_cd", length = 20)
    private String effectiveDurationUnitCd;

    @Column(name = "effective_from_time")
    private Instant effectiveFromTime;

    @Column(name = "effective_to_time")
    private Instant effectiveToTime;

    @Column(name = "form_cd", length = 20)
    private String formCd;

    @Column(name = "form_desc_txt", length = 100)
    private String formDescTxt;

    @Column(name = "interval_cd", length = 20)
    private String intervalCd;

    @Column(name = "interval_desc_txt", length = 100)
    private String intervalDescTxt;

    @Column(name = "rate_qty", length = 10)
    private String rateQty;

    @Column(name = "rate_qty_unit_cd", length = 20)
    private String rateQtyUnitCd;

    @Column(name = "route_cd", length = 20)
    private String routeCd;

    @Column(name = "route_desc_txt", length = 100)
    private String routeDescTxt;

    @Column(name = "status_cd")
    private Character statusCd;

    @Column(name = "status_time")
    private Instant statusTime;

}