package gov.cdc.nbs.patientlistener.request.create;

import gov.cdc.nbs.address.City;
import gov.cdc.nbs.address.Country;
import gov.cdc.nbs.address.County;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.message.patient.event.PatientCreateData;
import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.repository.PersonRepository;
import org.springframework.stereotype.Component;


import javax.transaction.Transactional;

@Component
class PatientCreator {

    private final PersonRepository repository;

    PatientCreator(final PersonRepository repository) {
        this.repository = repository;
    }

    @Transactional
    Person create(final PatientCreateData request) {

        Person person = new Person(asAdd(request));

        request.names().stream().map(name -> asName(request, name))
                .forEach(person::add);

        request.races().stream().map(race -> asRace(request, race))
                .forEach(person::add);

        request.addresses().stream().map(address -> asAddress(request, address))
                .forEach(person::add);

        request.phoneNumbers().stream().map(phoneNumber -> asPhoneNumber(request, phoneNumber))
                .forEach(person::add);

        request.emailAddresses().stream().map(emailAddress -> asEmailAddress(request, emailAddress))
                .forEach(person::add);

        return repository.save(person);
    }

    private PatientCommand.AddPatient asAdd(final PatientCreateData request) {
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
                request.asOf(),
                request.comments(),
                request.createdBy(),
                request.createdAt());
    }

    private PatientCommand.AddName asName(final PatientCreateData request, final PatientCreateData.Name name) {
        return new PatientCommand.AddName(
                request.patient(),
                name.first(),
                name.middle(),
                name.last(),
                name.suffix(),
                name.use(),
                request.createdBy(),
                request.createdAt());
    }

    private PatientCommand.AddRace asRace(final PatientCreateData request, final String race) {
        return new PatientCommand.AddRace(
                request.patient(),
                race,
                request.createdBy(),
                request.createdAt());
    }

    private PatientCommand.AddAddress asAddress(final PatientCreateData request,
            final PatientCreateData.PostalAddress address) {
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
                request.createdAt());
    }

    private PatientCommand.AddPhoneNumber asPhoneNumber(final PatientCreateData request,
            final PatientCreateData.PhoneNumber phoneNumber) {
        return new PatientCommand.AddPhoneNumber(
                request.patient(),
                phoneNumber.id(),
                phoneNumber.number(),
                phoneNumber.extension(),
                phoneNumber.phoneType(),
                request.createdBy(),
                request.createdAt());
    }

    private PatientCommand.AddEmailAddress asEmailAddress(final PatientCreateData request,
            final PatientCreateData.EmailAddress emailAddress) {
        return new PatientCommand.AddEmailAddress(
                request.patient(),
                emailAddress.id(),
                emailAddress.emailAddress(),
                request.createdBy(),
                request.createdAt());
    }
}
