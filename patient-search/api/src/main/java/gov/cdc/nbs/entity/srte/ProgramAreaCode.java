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
@Table(catalog = "NBS_SRTE", name = "Program_area_code")
public class ProgramAreaCode {
    @Id
    @Column(name = "prog_area_cd", nullable = false, length = 20)
    private String id;

    @Column(name = "prog_area_desc_txt", length = 50)
    private String progAreaDescTxt;

    @Column(name = "nbs_uid")
    private Integer nbsUid;

    @Column(name = "status_cd")
    private Character statusCd;

    @Column(name = "status_time")
    private Instant statusTime;

    @Column(name = "code_set_nm", length = 256)
    private String codeSetNm;

    @Column(name = "code_seq")
    private Short codeSeq;

    @OneToMany(mappedBy = "progAreaCd")
    private Set<ConditionCode> conditionCodes = new LinkedHashSet<>();

    @OneToMany(mappedBy = "defaultProgAreaCd")
    private Set<LabResult> labResults = new LinkedHashSet<>();

    @OneToMany(mappedBy = "defaultProgAreaCd")
    private Set<LabTest> labTests = new LinkedHashSet<>();

}