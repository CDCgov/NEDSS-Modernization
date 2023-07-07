package gov.cdc.nbs.entity.srte;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

import jakarta.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(catalog = "NBS_SRTE", name = "Labtest_Progarea_Mapping")
public class LabtestProgareaMapping implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EmbeddedId
    private LabtestProgareaMappingId id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "lab_test_cd", referencedColumnName = "lab_test_cd", nullable = false)
    @JoinColumn(name = "laboratory_id", referencedColumnName = "laboratory_id", nullable = false)
    private LabTest labTest;

    @Column(name = "lab_test_desc_txt", length = 100)
    private String labTestDescTxt;

    @Column(name = "test_type_cd", nullable = false, length = 20)
    private String testTypeCd;

    @Column(name = "condition_cd", length = 20)
    private String conditionCd;

    @Column(name = "condition_short_nm", length = 50)
    private String conditionShortNm;

    @Column(name = "condition_desc_txt", length = 300)
    private String conditionDescTxt;

    @Column(name = "prog_area_cd", nullable = false, length = 20)
    private String progAreaCd;

    @Column(name = "prog_area_desc_txt", nullable = false, length = 50)
    private String progAreaDescTxt;

    @Column(name = "organism_result_test_ind")
    private Character organismResultTestInd;

    @Column(name = "indent_level_nbr")
    private Short indentLevelNbr;

}
