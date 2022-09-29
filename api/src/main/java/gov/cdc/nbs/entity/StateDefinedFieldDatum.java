package gov.cdc.nbs.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.persistence.Entity;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "state_defined_field_data")
public class StateDefinedFieldDatum {
    @EmbeddedId
    private StateDefinedFieldDatumId id;

    @MapsId("ldfUid")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ldf_uid", nullable = false)
    private StateDefinedFieldMetadatum ldfUid;

    @Column(name = "add_time")
    private Instant addTime;

    @Column(name = "business_object_nm", nullable = false, length = 50)
    private String businessObjectNm;

    @Column(name = "last_chg_time")
    private Instant lastChgTime;

    @Column(name = "ldf_value", length = 2000)
    private String ldfValue;

    @Column(name = "version_ctrl_nbr")
    private Short versionCtrlNbr;

}