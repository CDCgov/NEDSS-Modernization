package gov.cdc.nbs.patient.create;

import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.id.IdGeneratorService;
import gov.cdc.nbs.message.patient.input.PatientInput;
import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.patient.RequestContext;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;

@Component
public class PatientCreator {

    private final PatientIdentifierGenerator patientIdentifierGenerator;
    private final IdGeneratorService idGeneratorService;
    private final EntityManager entityManager;
    private final PatientCreatedEmitter emitter;

    PatientCreator(
        final PatientIdentifierGenerator patientIdentifierGenerator,
        final IdGeneratorService idGenerator,
        final EntityManager entityManager,
        final PatientCreatedEmitter emitter
    ) {
        this.patientIdentifierGenerator = patientIdentifierGenerator;
        this.idGeneratorService = idGenerator;
        this.entityManager = entityManager;
        this.emitter = emitter;
    }

    @Transactional
    public PatientIdentifier create(final RequestContext context, final PatientInput input) {
        PatientIdentifier identifier = patientIdentifierGenerator.generate();

        Person person = new Person(asAdd(context, identifier, input));

        input.getNames().stream()
            .map(name -> asName(context, identifier, input.getAsOf(), name))
            .forEach(person::add);

        input.getRaces().stream()
            .map(race -> asRace(context, identifier, input.getAsOf(), race))
            .forEach(person::add);

        input.getAddresses().stream()
            .map(address -> asAddress(context, identifier, address))
            .forEach(person::add);

        input.getPhoneNumbers().stream()
            .map(phoneNumber -> asPhoneNumber(context, identifier, phoneNumber))
            .forEach(person::add);

        input.getEmailAddresses().stream()
            .map(emailAddress -> asEmailAddress(context, identifier, emailAddress))
            .forEach(person::add);

        input.getIdentifications().stream()
            .map(identification -> asIdentification(context, identifier, identification))
            .forEach(person::add);

        this.entityManager.persist(person);

        this.emitter.created(person);

        return identifier;
    }

    private PatientCommand.AddPatient asAdd(
        final RequestContext context,
        final PatientIdentifier identifier,
        final PatientInput request
    ) {
        return new PatientCommand.AddPatient(
            identifier.id(),
            identifier.local(),
            request.getDateOfBirth(),
            request.getBirthGender(),
            request.getCurrentGender(),
            request.getDeceased(),
            request.getDeceasedTime(),
            request.getMaritalStatus(),
            request.getEthnicity(),
            request.getAsOf(),
            request.getComments(),
            request.getStateHIVCase(),
            context.requestedBy(),
            context.requestedAt()
        );
    }

    private PatientCommand.AddName asName(
        final RequestContext context,
        final PatientIdentifier identifier,
        final Instant asOf,
        final PatientInput.Name name
    ) {
        String suffix = name.getSuffix() == null ? null : name.getSuffix().value();
        String type = name.getUse() == null ? null : name.getUse().name();
        return new PatientCommand.AddName(
            identifier.id(),
            asOf,
            name.getFirst(),
            name.getMiddle(),
            name.getLast(),
            suffix,
            type,
            context.requestedBy(),
            context.requestedAt()
        );
    }

    private PatientCommand.AddRace asRace(
        final RequestContext context,
        final PatientIdentifier identifier,
        final Instant asOf,
        final String race
    ) {
        return new PatientCommand.AddRace(
            identifier.id(),
            asOf,
            race,
            context.requestedBy(),
            context.requestedAt()
        );
    }

    private PatientCommand.AddAddress asAddress(
        final RequestContext context,
        final PatientIdentifier identifier,
        final PatientInput.PostalAddress address
    ) {
        return new PatientCommand.AddAddress(
            identifier.id(),
            generateNbsId(),
            address.getStreetAddress1(),
            address.getStreetAddress2(),
            address.getCity(),
            address.getState(),
            address.getZip(),
            address.getCounty(),
            address.getCountry(),
            address.getCensusTract(),
            context.requestedBy(),
            context.requestedAt()
        );
    }

    private PatientCommand.AddPhoneNumber asPhoneNumber(
        final RequestContext context,
        final PatientIdentifier identifier,
        final PatientInput.PhoneNumber phoneNumber
    ) {

        return new PatientCommand.AddPhoneNumber(
            identifier.id(),
            generateNbsId(),
            phoneNumber.getType(),
            phoneNumber.getUse(),
            phoneNumber.getNumber(),
            phoneNumber.getExtension(),
            context.requestedBy(),
            context.requestedAt()
        );
    }

    private PatientCommand.AddEmailAddress asEmailAddress(
        final RequestContext context,
        final PatientIdentifier identifier,
        final String emailAddress
    ) {
        return new PatientCommand.AddEmailAddress(
            identifier.id(),
            generateNbsId(),
            emailAddress,
            context.requestedBy(),
            context.requestedAt()
        );
    }

    private PatientCommand.AddIdentification asIdentification(
        final RequestContext context,
        final PatientIdentifier identifier,
        final PatientInput.Identification identification
    ) {
        return new PatientCommand.AddIdentification(
            identifier.id(),
            identification.getValue(),
            identification.getAuthority(),
            identification.getType(),
            context.requestedBy(),
            context.requestedAt()
        );
    }

    private Long generateNbsId() {
        var generatedId = idGeneratorService.getNextValidId(IdGeneratorService.EntityType.NBS);
        return generatedId.getId();
    }

}
