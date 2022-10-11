package gov.cdc.nbs.entity.srte;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(catalog = "NBS_SRTE", name = "Investigation_code")
public class InvestigationCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "investigation_cd", nullable = false, length = 20)
    private String id;

    @Column(name = "investigation_desc_txt")
    private String investigationDescTxt;

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

    @Column(name = "effective_from_time")
    private Instant effectiveFromTime;

    @Column(name = "effective_to_time")
    private Instant effectiveToTime;

    @Column(name = "status_cd", length = 1)
    private String statusCd;

    @Column(name = "status_time")
    private Instant statusTime;

    @Column(name = "nbs_uid")
    private Short nbsUid;

    @Column(name = "source_concept_id", length = 20)
    private String sourceConceptId;

    @Column(name = "code_set_nm", nullable = false, length = 256)
    private String codeSetNm;

    @Column(name = "seq_num", nullable = false)
    private Short seqNum;

}