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
@Table(catalog = "NBS_SRTE", name = "Language_code")
public class LanguageCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "code", nullable = false, length = 20)
    private String id;

    @Column(name = "assigning_authority_cd", length = 199)
    private String assigningAuthorityCd;

    @Column(name = "assigning_authority_desc_txt", length = 100)
    private String assigningAuthorityDescTxt;

    @Column(name = "code_desc_txt")
    private String codeDescTxt;

    @Column(name = "code_short_desc_txt", length = 50)
    private String codeShortDescTxt;

    @Column(name = "effective_from_time")
    private Instant effectiveFromTime;

    @Column(name = "effective_to_time")
    private Instant effectiveToTime;

    @Column(name = "key_info_txt", length = 2000)
    private String keyInfoTxt;

    @Column(name = "indent_level_nbr")
    private Short indentLevelNbr;

    @Column(name = "is_modifiable_ind")
    private Character isModifiableInd;

    @Column(name = "parent_is_cd", length = 20)
    private String parentIsCd;

    @Column(name = "nbs_uid", nullable = false)
    private Integer nbsUid;

    @Column(name = "status_cd", length = 1)
    private String statusCd;

    @Column(name = "status_time")
    private Instant statusTime;

    @Column(name = "code_set_nm", length = 256)
    private String codeSetNm;

    @Column(name = "seq_num")
    private Short seqNum;

    @Column(name = "source_concept_id", length = 20)
    private String sourceConceptId;

}