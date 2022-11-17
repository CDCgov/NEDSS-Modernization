package gov.cdc.nbs.entity.odse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.persistence.Entity;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Obs_value_txt_hist")
public class ObsValueTxtHist {
    @EmbeddedId
    private ObsValueTxtHistId id;

    @MapsId
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumns({
            @JoinColumn(name = "observation_uid", referencedColumnName = "observation_uid", nullable = false),
            @JoinColumn(name = "obs_value_txt_seq", referencedColumnName = "obs_value_txt_seq", nullable = false)
    })
    private ObsValueTxt obsValueTxt;

    @Column(name = "data_subtype_cd", length = 20)
    private String dataSubtypeCd;

    @Column(name = "encoding_type_cd", length = 20)
    private String encodingTypeCd;

    @Column(name = "txt_type_cd", length = 20)
    private String txtTypeCd;

    @Column(name = "value_image_txt", columnDefinition = "IMAGE")
    private byte[] valueImageTxt;

    @Column(name = "value_txt", length = 2000)
    private String valueTxt;

}