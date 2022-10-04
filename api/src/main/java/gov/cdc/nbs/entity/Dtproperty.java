package gov.cdc.nbs.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

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