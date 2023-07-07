package gov.cdc.nbs.entity.odse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import jakarta.persistence.Entity;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Obs_value_txt")
public class ObsValueTxt {
    @EmbeddedId
    private ObsValueTxtId id;

    @MapsId("observationUid")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "observation_uid", nullable = false)
    private Observation observationUid;

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
