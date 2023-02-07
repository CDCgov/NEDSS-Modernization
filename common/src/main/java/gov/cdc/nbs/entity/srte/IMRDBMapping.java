package gov.cdc.nbs.entity.srte;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class IMRDBMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IMRDBMapping_id", nullable = false)
    private Integer id;

    @Column(name = "unique_cd")
    private String uniqueCd;

    @Column(name = "unique_name")
    private String uniqueName;

    @Column(name = "description")
    private String description;

    @Column(name = "DB_table")
    private String dbTable;

    @Column(name = "DB_field")
    private String dbField;

    @Column(name = "RDB_table")
    private String rdbTable;

    @Column(name = "RDB_attribute")
    private String rdbAttribute;

    @Column(name = "other_attributes")
    private String otherAttributes;

    @Column(name = "condition_cd")
    private String conditionCd;

}