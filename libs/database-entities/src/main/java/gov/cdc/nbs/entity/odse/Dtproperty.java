package gov.cdc.nbs.entity.odse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "dtproperties")
public class Dtproperty {
    @EmbeddedId
    private DtpropertyId id;

    @Column(name = "objectid")
    private Integer objectid;

    @Column(name = "value")
    private String value;

    @Column(name = "uvalue", columnDefinition = "NVARCHAR")
    private String uvalue;

    @Lob
    @Column(name = "lvalue", columnDefinition = "IMAGE")
    private byte[] lvalue;

    @Column(name = "version", nullable = false)
    private Integer version;

}
