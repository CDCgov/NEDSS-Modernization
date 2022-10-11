package gov.cdc.nbs.entity.srte;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(catalog = "NBS_SRTE", name = "Specimen_source_code")
public class SpecimenSourceCode {
    @Id
    @Column(name = "specimen_source_uid", nullable = false)
    private Long id;

    @Column(name = "code_set_nm", nullable = false, length = 256)
    private String codeSetNm;

    @Column(name = "seq_num", nullable = false)
    private Short seqNum;

    @Column(name = "code", nullable = false, length = 20)
    private String code;

    @Column(name = "specimen_source_desc_txt", length = 300)
    private String specimenSourceDescTxt;

    @Column(name = "code_system_cd", length = 300)
    private String codeSystemCd;

    @Column(name = "code_system_desc_txt", length = 100)
    private String codeSystemDescTxt;

    @Column(name = "assigning_authority_cd", length = 199)
    private String assigningAuthorityCd;

    @Column(name = "assigning_authority_desc_txt", length = 100)
    private String assigningAuthorityDescTxt;

    @Column(name = "is_modifiable_ind")
    private Character isModifiableInd;

    @Column(name = "status_cd")
    private Character statusCd;

    @Column(name = "status_time")
    private Instant statusTime;

}