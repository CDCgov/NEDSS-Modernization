package gov.cdc.nbs.patientlistener.odse;

/*
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;*/

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import javax.persistence.Lob;
import javax.persistence.OneToOne;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Obs_value_coded")
public class ObsValueCoded {
    @EmbeddedId
    private ObsValueCodedId id;

    @MapsId("observationUid")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "observation_uid", nullable = false)
    private Observation observationUid;

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