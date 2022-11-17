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
@Table(catalog = "NBS_SRTE", name = "State_code")
public class StateCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "state_cd", nullable = false, length = 20)
    private String id;

    @Column(name = "assigning_authority_cd", length = 199)
    private String assigningAuthorityCd;

    @Column(name = "assigning_authority_desc_txt", length = 100)
    private String assigningAuthorityDescTxt;

    @Column(name = "state_nm", length = 2)
    private String stateNm;

    @Column(name = "code_desc_txt", length = 50)
    private String codeDescTxt;

    @Column(name = "effective_from_time")
    private Instant effectiveFromTime;

    @Column(name = "effective_to_time")
    private Instant effectiveToTime;

    @Column(name = "excluded_txt", length = 1300)
    private String excludedTxt;

    @Column(name = "indent_level_nbr")
    private Short indentLevelNbr;

    @Column(name = "is_modifiable_ind")
    private Character isModifiableInd;

    @Column(name = "key_info_txt", length = 2000)
    private String keyInfoTxt;

    @Column(name = "parent_is_cd", length = 20)
    private String parentIsCd;

    @Column(name = "status_cd", length = 1)
    private String statusCd;

    @Column(name = "status_time")
    private Instant statusTime;

    @Column(name = "code_set_nm", length = 256)
    private String codeSetNm;

    @Column(name = "seq_num")
    private Short seqNum;

    @Column(name = "nbs_uid")
    private Integer nbsUid;

    @Column(name = "source_concept_id", length = 20)
    private String sourceConceptId;

    @Column(name = "code_system_cd", length = 300)
    private String codeSystemCd;

    @Column(name = "code_system_desc_txt", length = 100)
    private String codeSystemDescTxt;

}