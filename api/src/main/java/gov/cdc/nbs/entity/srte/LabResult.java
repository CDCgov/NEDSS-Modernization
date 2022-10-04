package gov.cdc.nbs.entity.srte;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(catalog = "NBS_SRTE", name = "Lab_result")
public class LabResult {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EmbeddedId
    private LabResultId id;

    @MapsId("laboratoryId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "laboratory_id", nullable = false)
    private LabCodingSystem laboratory;

    @Column(name = "lab_result_desc_txt", length = 50)
    private String labResultDescTxt;

    @Column(name = "effective_from_time")
    private Instant effectiveFromTime;

    @Column(name = "effective_to_time")
    private Instant effectiveToTime;

    @Column(name = "nbs_uid")
    private Long nbsUid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "default_prog_area_cd")
    private ProgramAreaCode defaultProgAreaCd;

    @Column(name = "organism_name_ind")
    private Character organismNameInd;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "default_condition_cd")
    private ConditionCode defaultConditionCd;

    @Column(name = "pa_derivation_exclude_cd")
    private Character paDerivationExcludeCd;

    @Column(name = "code_system_cd", length = 300)
    private String codeSystemCd;

    @Column(name = "code_set_nm", length = 256)
    private String codeSetNm;

    @OneToMany(mappedBy = "labResult")
    private Set<LabResultSnomed> labResultSnomeds = new LinkedHashSet<>();

}