package gov.cdc.nbs.entity.srte;

import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
@Table(catalog = "NBS_SRTE", name = "Lab_coding_system")
public class LabCodingSystem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "laboratory_id", nullable = false, length = 20)
    private String id;

    @Column(name = "laboratory_system_desc_txt", length = 100)
    private String laboratorySystemDescTxt;

    @Column(name = "coding_system_cd", length = 20)
    private String codingSystemCd;

    @Column(name = "code_system_desc_txt", length = 100)
    private String codeSystemDescTxt;

    @Column(name = "electronic_lab_ind")
    private Character electronicLabInd;

    @Column(name = "effective_from_time")
    private Instant effectiveFromTime;

    @Column(name = "effective_to_time")
    private Instant effectiveToTime;

    @Column(name = "assigning_authority_cd", length = 199)
    private String assigningAuthorityCd;

    @Column(name = "assigning_authority_desc_txt", length = 100)
    private String assigningAuthorityDescTxt;

    @Column(name = "nbs_uid")
    private Long nbsUid;

    @OneToMany(mappedBy = "laboratory")
    private Set<LabResult> labResults = new LinkedHashSet<>();

    @OneToMany(mappedBy = "laboratory")
    private Set<LabTest> labTests = new LinkedHashSet<>();

}