package gov.cdc.nbs.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Entity")
public class NBSEntity {
    @Id
    @Column(name = "entity_uid", nullable = false)
    private Long id;

    @Column(name = "class_cd", nullable = false, length = 10)
    private String classCd;

}