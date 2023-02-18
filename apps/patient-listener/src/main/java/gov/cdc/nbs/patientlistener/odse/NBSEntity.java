package gov.cdc.nbs.patientlistener.odse;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    public NBSEntity(Long id, String classCd) {
        this.id = id;
        this.classCd = classCd;
    }

    @OneToMany(mappedBy = "id.subjectEntityUid", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Participation> participations;

    @OneToMany(mappedBy = "id.entityUid", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<EntityLocatorParticipation> entityLocatorParticipations;

}