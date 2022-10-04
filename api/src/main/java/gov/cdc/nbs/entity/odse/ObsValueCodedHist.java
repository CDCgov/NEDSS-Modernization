package gov.cdc.nbs.entity.odse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Obs_value_coded_hist")
public class ObsValueCodedHist {
    @EmbeddedId
    private ObsValueCodedHistId id;

    @Column(name = "code_system_cd", length = 300)
    private String codeSystemCd;

    @Column(name = "code_system_desc_txt", length = 100)
    private String codeSystemDescTxt;

    @Column(name = "code_version", length = 10)
    private String codeVersion;

    @Column(name = "display_name", length = 300)
    private String displayName;

    @Column(name = "original_txt", length = 300)
    private String originalTxt;

    @Column(name = "alt_cd", length = 50)
    private String altCd;

    @Column(name = "alt_cd_desc_txt", length = 100)
    private String altCdDescTxt;

    @Column(name = "alt_cd_system_cd", length = 300)
    private String altCdSystemCd;

    @Column(name = "alt_cd_system_desc_txt", length = 100)
    private String altCdSystemDescTxt;

    @Column(name = "code_derived_ind")
    private Character codeDerivedInd;

}