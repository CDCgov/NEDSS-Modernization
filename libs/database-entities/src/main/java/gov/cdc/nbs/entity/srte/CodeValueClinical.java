package gov.cdc.nbs.entity.srte;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(catalog = "NBS_SRTE", name = "Code_value_clinical")
public class CodeValueClinical implements Serializable {
    @EmbeddedId
    private CodeValueClinicalId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "snomed_cd")
    private SnomedCode snomedCd;

    @Column(name = "assigning_authority_cd", length = 199)
    private String assigningAuthorityCd;

    @Column(name = "assigning_authority_desc_txt", length = 100)
    private String assigningAuthorityDescTxt;

    @Column(name = "order_number", nullable = false)
    private Short orderNumber;

    @Column(name = "code_desc_txt", length = 300)
    private String codeDescTxt;

    @Column(name = "code_short_desc_txt", length = 50)
    private String codeShortDescTxt;

    @Column(name = "code_system_code", length = 300)
    private String codeSystemCode;

    @Column(name = "code_system_desc_txt", length = 100)
    private String codeSystemDescTxt;

    @Column(name = "common_name", length = 300)
    private String commonName;

    @Column(name = "other_names", length = 300)
    private String otherNames;

    @Column(name = "status_cd")
    private Character statusCd;

    @Column(name = "status_time")
    private Instant statusTime;

}
