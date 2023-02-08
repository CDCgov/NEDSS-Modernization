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
@Table(catalog = "NBS_SRTE", name = "Country_Code_ISO")
public class CountryCodeIso {
    @EmbeddedId
    private CountryCodeIsoId id;

    @Column(name = "code_desc_txt", length = 100)
    private String codeDescTxt;

    @Column(name = "code_system_cd", length = 300)
    private String codeSystemCd;

    @Column(name = "code_system_desc_txt", length = 100)
    private String codeSystemDescTxt;

    @Column(name = "code_short_desc_txt", length = 100)
    private String codeShortDescTxt;

    @Column(name = "assigning_authority_cd", length = 199)
    private String assigningAuthorityCd;

    @Column(name = "assigning_authority_desc_txt", length = 100)
    private String assigningAuthorityDescTxt;

    @Column(name = "status_cd", length = 1)
    private String statusCd;

    @Column(name = "status_time")
    private Instant statusTime;

    @OneToMany(mappedBy = "countryCodeIso")
    private Set<CountryXref> countryXrefs = new LinkedHashSet<>();

}