package gov.cdc.nbs.patient.create;

import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.id.IdGeneratorService;
import gov.cdc.nbs.message.patient.input.PatientInput;
import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.patient.PatientIdentifierGenerator;
import gov.cdc.nbs.patient.RequestContext;
import gov.cdc.nbs.patient.demographic.name.SoundexResolver;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

@Component
public class BasicPatientCreator {

  private final SoundexResolver resolver;
  private final PatientIdentifierGenerator patientIdentifierGenerator;
  private final IdGeneratorService idGeneratorService;
  private final EntityManager entityManager;
  private final PatientCreatedEmitter emitter;

  BasicPatientCreator(
      final SoundexResolver resolver,
      final PatientIdentifierGenerator patientIdentifierGenerator,
      final IdGeneratorService idGenerator,
      final EntityManager entityManager,
      final PatientCreatedEmitter emitter
  ) {
    this.resolver = resolver;
    this.patientIdentifierGenerator = patientIdentifierGenerator;
    this.idGeneratorService = idGenerator;
    this.entityManager = entityManager;
    this.emitter = emitter;
  }

  @Transactional
  public PatientIdentifier create(final RequestContext context, final PatientInput input) {
    PatientIdentifier identifier = patientIdentifierGenerator.generate();

    Person person = new Person(asAdd(context, identifier, input));

    Instant asOf = input.getAsOf();
    LocalDate localAsOf = input.getAsOf().atZone(ZoneId.systemDefault()).toLocalDate();

    input.getNames().stream()
        .map(name -> asName(context, identifier, localAsOf, name))
        .forEach(name -> person.add(resolver, name));

    input.getRaces().stream()
        .map(race -> asRace(context, identifier, localAsOf, race))
        .forEach(person::add);

    input.getAddresses().stream()
        .map(address -> asAddress(context, identifier, asOf, address))
        .forEach(person::add);

    input.getPhoneNumbers().stream()
        .map(phoneNumber -> asPhoneNumber(context, identifier, asOf, phoneNumber))
        .forEach(person::add);

    input.getEmailAddresses().stream()
        .map(emailAddress -> asEmailAddress(context, identifier, asOf, emailAddress))
        .forEach(person::add);

    input.getIdentifications().stream()
        .map(identification -> asIdentification(context, identifier, localAsOf, identification))
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
      final LocalDate asOf,
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
      final LocalDate asOf,
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
      final Instant asOf,
      final PatientInput.PostalAddress address
  ) {
    return new PatientCommand.AddAddress(
        identifier.id(),
        generateNbsId(),
        asOf,
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
      final Instant asOf,
      final PatientInput.PhoneNumber phoneNumber
  ) {

    return new PatientCommand.AddPhoneNumber(
        identifier.id(),
        generateNbsId(),
        asOf,
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
      final Instant asOf,
      final String emailAddress
  ) {
    return new PatientCommand.AddEmailAddress(
        identifier.id(),
        generateNbsId(),
        asOf,
        emailAddress,
        context.requestedBy(),
        context.requestedAt()
    );
  }

  private PatientCommand.AddIdentification asIdentification(
      final RequestContext context,
      final PatientIdentifier identifier,
      final LocalDate asOf,
      final PatientInput.Identification identification
  ) {
    return new PatientCommand.AddIdentification(
        identifier.id(),
        asOf,
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
