package gov.cdc.nbs.entity.odse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Obs_value_coded_mod_hist")
public class ObsValueCodedModHist {
    @EmbeddedId
    private ObsValueCodedModHistId id;

    @Column(name = "code_system_cd", length = 20)
    private String codeSystemCd;

    @Column(name = "code_system_desc_txt", length = 100)
    private String codeSystemDescTxt;

    @Column(name = "code_version", length = 10)
    private String codeVersion;

    @Column(name = "display_name", length = 300)
    private String displayName;

    @Column(name = "original_txt", length = 100)
    private String originalTxt;

}
