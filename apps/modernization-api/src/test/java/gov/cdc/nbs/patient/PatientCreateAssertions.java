package gov.cdc.nbs.patient;

import gov.cdc.nbs.entity.odse.EntityId;
import gov.cdc.nbs.entity.odse.PersonName;
import gov.cdc.nbs.entity.odse.PersonRace;
import gov.cdc.nbs.entity.odse.PostalEntityLocatorParticipation;
import gov.cdc.nbs.entity.odse.PostalLocator;
import gov.cdc.nbs.entity.odse.TeleEntityLocatorParticipation;
import gov.cdc.nbs.entity.odse.TeleLocator;
import gov.cdc.nbs.message.patient.input.PatientInput;

import java.util.Collection;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;

public class PatientCreateAssertions {

    public static Consumer<PersonName> containsNames(final Collection<PatientInput.Name> names) {
        return names.stream()
            .reduce(
                c -> {
                },
                (existing, next) -> existing.andThen(containsName(next)),
                Consumer::andThen
            );
    }

    private static Consumer<PersonName> containsName(final PatientInput.Name name) {
        return actual -> assertThat(actual)
            .returns(name.getFirst(), PersonName::getFirstNm)
            .returns(name.getLast(), PersonName::getLastNm);
    }

    public static Consumer<PersonRace> containsRaceCategories(final Collection<String> races) {
        return races.stream()
            .reduce(
                c -> {
                },
                (existing, next) -> existing.andThen(containsRaceCategory(next)),
                Consumer::andThen
            );
    }

    private static Consumer<PersonRace> containsRaceCategory(final String race) {
        return actual -> assertThat(actual)
            .returns(race, PersonRace::getRaceCategoryCd)
            .returns(race, PersonRace::getRaceCd)
            .extracting(PersonRace::getAsOfDate).isNotNull()
            ;
    }

    public static Consumer<PostalEntityLocatorParticipation> containsAddresses(final Collection<PatientInput.PostalAddress> addresses) {
        return addresses.stream()
            .reduce(
                c -> {
                },
                (existing, next) -> existing.andThen(containsAddress(next)),
                Consumer::andThen
            );
    }

    private static Consumer<PostalEntityLocatorParticipation> containsAddress(final PatientInput.PostalAddress address) {
        return actual -> assertThat(actual)
            .extracting(PostalEntityLocatorParticipation::getLocator)
            .returns(address.getStreetAddress1(), PostalLocator::getStreetAddr1)
            .returns(address.getStreetAddress2(), PostalLocator::getStreetAddr2)
            .returns(address.getCity(), PostalLocator::getCityDescTxt)
            .returns(address.getState(), PostalLocator::getStateCd)
            .returns(address.getZip(), PostalLocator::getZipCd)
            .returns(address.getCounty(), PostalLocator::getCntyCd)
            .returns(address.getCountry(), PostalLocator::getCntryCd)
            .returns(address.getCensusTract(), PostalLocator::getCensusTract)
            ;
    }

    public static Consumer<TeleEntityLocatorParticipation> containsPhoneNumbers(final Collection<PatientInput.PhoneNumber> phoneNumbers) {
        return phoneNumbers.stream()
            .reduce(
                c -> {
                },
                (existing, next) -> existing.andThen(containsPhoneNumber(next)),
                Consumer::andThen
            );
    }

    private static Consumer<TeleEntityLocatorParticipation> containsPhoneNumber(final PatientInput.PhoneNumber phone) {
        return actual -> assertThat(actual)
            .returns(phone.getType(), TeleEntityLocatorParticipation::getCd)
            .returns(phone.getUse(), TeleEntityLocatorParticipation::getUseCd)
            .extracting(TeleEntityLocatorParticipation::getLocator)
            .returns(phone.getNumber(), TeleLocator::getPhoneNbrTxt)
            .returns(phone.getExtension(), TeleLocator::getExtensionTxt)
            .returns("ACTIVE", locator -> locator.recordStatus().status())
            ;
    }

    public static Consumer<TeleEntityLocatorParticipation> containsEmailAddresses(final Collection<String> addresses) {
        return addresses.stream()
            .reduce(
                c -> {
                },
                (existing, next) -> existing.andThen(containsEmailAddress(next)),
                Consumer::andThen
            );
    }

    private static Consumer<TeleEntityLocatorParticipation> containsEmailAddress(final String email) {
        return actual -> assertThat(actual)
            .returns("NET", TeleEntityLocatorParticipation::getCd)
            .returns("H", TeleEntityLocatorParticipation::getUseCd)
            .extracting(TeleEntityLocatorParticipation::getLocator)
            .returns(email, TeleLocator::getEmailAddress)
            .returns("ACTIVE", locator -> locator.recordStatus().status())
            ;
    }

    public static Consumer<EntityId> containsIdentifications(final Collection<PatientInput.Identification> identifications) {
        return identifications.stream()
            .reduce(
                c -> {
                },
                (existing, next) -> existing.andThen(containsIdentification(next)),
                Consumer::andThen
            );
    }

    private static Consumer<EntityId> containsIdentification(final PatientInput.Identification identification) {
        return actual -> assertThat(actual)
            .returns(identification.getValue(), EntityId::getRootExtensionTxt)
            .returns(identification.getAuthority(), EntityId::getAssigningAuthorityCd)
            .returns(identification.getType(), EntityId::getTypeCd)
            ;
    }

    private PatientCreateAssertions() {
    }
}
