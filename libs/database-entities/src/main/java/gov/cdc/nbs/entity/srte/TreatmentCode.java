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
@Table(catalog = "NBS_SRTE", name = "Treatment_code")
public class TreatmentCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "treatment_cd", nullable = false, length = 20)
    private String id;

    @Column(name = "treatment_desc_txt", length = 300)
    private String treatmentDescTxt;

    @Column(name = "assigning_authority_cd", length = 199)
    private String assigningAuthorityCd;

    @Column(name = "assigning_authority_desc_txt", length = 100)
    private String assigningAuthorityDescTxt;

    @Column(name = "code_system_cd", length = 300)
    private String codeSystemCd;

    @Column(name = "code_system_desc_txt", length = 80)
    private String codeSystemDescTxt;

    @Column(name = "code_version", length = 10)
    private String codeVersion;

    @Column(name = "treatment_type_cd")
    private Character treatmentTypeCd;

    @Column(name = "nbs_uid")
    private Short nbsUid;

    @Column(name = "effective_from_time")
    private Instant effectiveFromTime;

    @Column(name = "effective_to_time")
    private Instant effectiveToTime;

    @Column(name = "status_cd", length = 1)
    private String statusCd;

    @Column(name = "status_time")
    private Instant statusTime;

    @Column(name = "source_concept_id", length = 20)
    private String sourceConceptId;

    @Column(name = "code_set_nm", length = 256)
    private String codeSetNm;

    @Column(name = "seq_num", nullable = false)
    private Short seqNum;

    @Column(name = "drug_cd", length = 20)
    private String drugCd;

    @Column(name = "drug_desc_txt")
    private String drugDescTxt;

    @Column(name = "dose_qty", length = 20)
    private String doseQty;

    @Column(name = "dose_qty_unit_cd", length = 10)
    private String doseQtyUnitCd;

    @Column(name = "route_cd", length = 20)
    private String routeCd;

    @Column(name = "route_desc_txt")
    private String routeDescTxt;

    @Column(name = "interval_cd", length = 20)
    private String intervalCd;

    @Column(name = "interval_desc_txt", length = 300)
    private String intervalDescTxt;

    @Column(name = "duration_amt", length = 20)
    private String durationAmt;

    @Column(name = "duration_unit_cd", length = 20)
    private String durationUnitCd;

}
