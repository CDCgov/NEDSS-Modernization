package gov.cdc.nbs.patientlistener.service;

import gov.cdc.nbs.address.City;
import gov.cdc.nbs.address.Country;
import gov.cdc.nbs.address.County;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.message.PatientCreateRequest;
import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.repository.PersonRepository;

import javax.transaction.Transactional;

class PatientCreator {

    private final PersonRepository repository;

    PatientCreator(final PersonRepository repository) {
        this.repository = repository;
    }


    @Transactional
    Person create(final PatientCreateRequest request) {

        Person person = new Person(asAdd(request));

        request.names().stream().map(name -> as(request, name))
                .forEach(person::add);

        request.races().stream().map(race -> asRace(request, race))
                .forEach(person::add);

        request.addresses().stream().map(address -> as(request, address))
                .forEach(person::add);

        request.phoneNumbers().stream().map(phoneNumber -> as(request, phoneNumber))
                .forEach(person::add);

        request.emailAddresses().stream().map(emailAddress -> asEmailAddress(request, emailAddress))
                .forEach(person::add);


        return repository.save(person);
    }

    private PatientCommand.AddPatient asAdd(final PatientCreateRequest request) {
        return new PatientCommand.AddPatient(
                request.patient(),
                request.patientLocalId(),
                request.ssn(),
                request.dateOfBirth(),
                request.birthGender(),
                request.currentGender(),
                request.deceased(),
                request.deceasedTime(),
                request.maritalStatus(),
                request.ethnicity(),
                request.createdBy(),
                request.createdAt()
        );
    }

    private PatientCommand.AddName as(final PatientCreateRequest request, final PatientCreateRequest.Name name) {
        return new PatientCommand.AddName(
                request.patient(),
                name.first(),
                name.middle(),
                name.last(),
                name.suffix(),
                name.use(),
                request.createdBy(),
                request.createdAt()
        );
    }

    private PatientCommand.AddRace asRace(final PatientCreateRequest request, final String race) {
        return new PatientCommand.AddRace(
                request.patient(),
                race,
                request.createdBy(),
                request.createdAt()
        );
    }

    private PatientCommand.AddAddress as(final PatientCreateRequest request, final PatientCreateRequest.PostalAddress address) {
        return new PatientCommand.AddAddress(
                request.patient(),
                address.id(),
                address.streetAddress1(),
                address.streetAddress2(),
                new City(address.city()),
                address.stateCode(),
                address.zip(),
                new County(address.countyCode()),
                new Country(address.countryCode()),
                address.censusTract(),
                request.createdBy(),
                request.createdAt()
        );
    }

    private PatientCommand.AddPhoneNumber as(final PatientCreateRequest request, final PatientCreateRequest.PhoneNumber phoneNumber) {
        return new PatientCommand.AddPhoneNumber(
                request.patient(),
                phoneNumber.id(),
                phoneNumber.number(),
                phoneNumber.extension(),
                phoneNumber.phoneType(),
                request.createdBy(),
                request.createdAt()
        );
    }

    private PatientCommand.AddEmailAddress asEmailAddress(final PatientCreateRequest request, final PatientCreateRequest.EmailAddress emailAddress) {
        return new PatientCommand.AddEmailAddress(
                request.patient(),
                emailAddress.id(),
                emailAddress.emailAddress(),
                request.createdBy(),
                request.createdAt()
        );
    }
}
