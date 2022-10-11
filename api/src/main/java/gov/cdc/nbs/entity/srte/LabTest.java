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
@Table(catalog = "NBS_SRTE", name = "Lab_test")
public class LabTest {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EmbeddedId
    private LabTestId id;

    @MapsId("laboratoryId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "laboratory_id", nullable = false)
    private LabCodingSystem laboratory;

    @Column(name = "lab_test_desc_txt", length = 100)
    private String labTestDescTxt;

    @Column(name = "test_type_cd", nullable = false, length = 20)
    private String testTypeCd;

    @Column(name = "nbs_uid", nullable = false)
    private Long nbsUid;

    @Column(name = "effective_from_time")
    private Instant effectiveFromTime;

    @Column(name = "effective_to_time")
    private Instant effectiveToTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "default_prog_area_cd")
    private ProgramAreaCode defaultProgAreaCd;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "default_condition_cd")
    private ConditionCode defaultConditionCd;

    @Column(name = "drug_test_ind")
    private Character drugTestInd;

    @Column(name = "organism_result_test_ind")
    private Character organismResultTestInd;

    @Column(name = "indent_level_nbr")
    private Short indentLevelNbr;

    @Column(name = "pa_derivation_exclude_cd")
    private Character paDerivationExcludeCd;

    @OneToMany(mappedBy = "labTest")
    private Set<LabtestLoinc> labtestLoincs = new LinkedHashSet<>();

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "labTest")
    private LabtestProgareaMapping labtestProgareaMapping;

}