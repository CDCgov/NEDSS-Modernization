package gov.cdc.nbs.patient.profile;

import gov.cdc.nbs.entity.enums.RecordStatus;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.patient.PatientAssociationCountFinder;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import gov.cdc.nbs.repository.PersonRepository;

@Controller
class PatientProfileDeletableResolver {

    @Autowired
    private final PersonRepository personRepository;
    private final PatientAssociationCountFinder finder;

    PatientProfileDeletableResolver(final PatientAssociationCountFinder finder, final PersonRepository personRepository) {
        this.finder = finder;
        this.personRepository = personRepository;
    }

    @SchemaMapping("deletable")
    boolean resolve(final PatientProfile profile) {
        Optional<Person> person = personRepository.findById(profile.id());
        boolean personDeleted = person.isPresent() ? person.get().getRecordStatusCd().equals(RecordStatus.LOG_DEL) : true;
        return !personDeleted && finder.count(profile.id()) == 0;
    }
}
