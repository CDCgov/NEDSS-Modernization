package gov.cdc.nbs.entity.odse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "DF_sf_mdata_field_group")
public class DfSfMdataFieldGroup {
    @EmbeddedId
    private DfSfMdataFieldGroupId id;

    @MapsId("dfSfMetadataGroupUid")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "df_sf_metadata_group_uid", nullable = false)
    private DfSfMetadataGroup dfSfMetadataGroupUid;

    @Column(name = "field_type", nullable = false, length = 20)
    private String fieldType;

    @Column(name = "version_ctrl_nbr", nullable = false)
    private Short versionCtrlNbr;

}
