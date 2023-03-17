package gov.cdc.nbs.entity.odse;

import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.patient.PatientCommand.AddMortalityLocator;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "id.subjectEntityUid", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Participation> participations;

    @OneToMany(
            mappedBy = "id.entityUid",
            fetch = FetchType.EAGER,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE,
                    CascadeType.REMOVE
            },
            orphanRemoval = true)
    private List<EntityLocatorParticipation> entityLocatorParticipations;

    protected NBSEntity() {}

    public NBSEntity(Long id, String classCd) {
        this.id = id;
        this.classCd = classCd;
    }

    public NBSEntity(final PatientCommand.AddPatient patient) {
        this(patient.person(), "PSN");
    }

    private List<EntityLocatorParticipation> ensureLocators() {
        if (this.entityLocatorParticipations == null) {
            this.entityLocatorParticipations = new ArrayList<>();
        }

        return this.entityLocatorParticipations;
    }

    public EntityLocatorParticipation add(
            final PatientCommand.AddAddress address) {

        List<EntityLocatorParticipation> locators = ensureLocators();

        EntityLocatorParticipationId identifier = new EntityLocatorParticipationId(this.id, address.id());

        EntityLocatorParticipation participation = new PostalEntityLocatorParticipation(
                this,
                identifier,
                address);

        locators.add(participation);


        return participation;
    }

    public EntityLocatorParticipation add(final PatientCommand.AddPhoneNumber phoneNumber) {
        List<EntityLocatorParticipation> locators = ensureLocators();

        EntityLocatorParticipationId identifier = new EntityLocatorParticipationId(this.id, phoneNumber.id());

        EntityLocatorParticipation participation = new TeleEntityLocatorParticipation(
                this,
                identifier,
                phoneNumber);

        locators.add(participation);


        return participation;
    }

    public EntityLocatorParticipation add(final PatientCommand.AddEmailAddress emailAddress) {
        List<EntityLocatorParticipation> locators = ensureLocators();

        EntityLocatorParticipationId identifier = new EntityLocatorParticipationId(this.id, emailAddress.id());

        EntityLocatorParticipation participation = new TeleEntityLocatorParticipation(
                this,
                identifier,
                emailAddress);

        locators.add(participation);


        return participation;
    }

    public EntityLocatorParticipation add(AddMortalityLocator mortality) {
        List<EntityLocatorParticipation> locators = ensureLocators();

        EntityLocatorParticipationId identifier = new EntityLocatorParticipationId(this.id, mortality.id());

        EntityLocatorParticipation participation = new PostalEntityLocatorParticipation(
                this,
                identifier,
                mortality);

        locators.add(participation);

        return participation;
    }
}
