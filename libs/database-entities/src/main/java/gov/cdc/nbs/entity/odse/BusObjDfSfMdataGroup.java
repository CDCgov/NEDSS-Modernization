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
@Table(name = "Bus_obj_df_sf_mdata_group")
public class BusObjDfSfMdataGroup {
    @Id
    @Column(name = "business_object_uid", nullable = false)
    private Long id;

    @Column(name = "version_ctrl_nbr", nullable = false)
    private Short versionCtrlNbr;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "df_sf_metadata_group_uid", nullable = false)
    private DfSfMetadataGroup dfSfMetadataGroupUid;

}
