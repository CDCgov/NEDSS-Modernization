import {
    AddressDemographic,
    AdministrativeInformation,
    EthnicityDemographic,
    GeneralInformationDemographic,
    IdentificationDemographic,
    MortalityDemographic,
    NameDemographic,
    PatientDemographics,
    PhoneEmailDemographic,
    RaceDemographic,
    SexBirthDemographic
} from 'libs/patient/demographics';
import { PatientDemographicsData } from '../demographics';
import { internalizeDate } from 'date';
import { maybeMap } from 'utils/mapping';

/**
 * Resolves all of the demographics providers combining the results into a single instance of `PatientDemographics`
 *
 * @param {PatientDemographicsData} data The demographics data for a patient.
 * @return {Promise} A Promise containing the `PatientDemographics`
 */
const evaluated = (data: PatientDemographicsData): Promise<PatientDemographics> =>
    Promise.all([
        data.administrative.get().then(asAdministrative),
        data.names.get().then(asNames),
        data.addresses.get().then(asAddresses),
        data.phoneEmail.get().then(asPhoneEmails),
        data.identifications.get().then(asIdentifications),
        data.race.get().then(asRaces),
        data.ethnicity.get().then(asEthnicity),
        data.sexBirth.get().then((resolved) => asSexBirth(resolved.demographic)),
        data.mortality.get().then(asMortality),
        data.general.get().then(asGeneralInformation)
    ]).then((resolved) => resolved.reduce((current, next) => ({ ...current, ...next }), {}));

export { evaluated };

const asAdministrative = (demographic: AdministrativeInformation) => ({
    administrative: { ...demographic, asOf: internalizeDate(demographic.asOf) }
});

const asNames = maybeMap((demographics: NameDemographic[]) => ({ names: demographics.map(asName) }));
const asName = (demographic: NameDemographic) => ({ ...demographic });

const asAddresses = maybeMap((demographics: AddressDemographic[]) => ({ addresses: demographics.map(asAddress) }));
const asAddress = (demographic: AddressDemographic) => ({ ...demographic });

const asPhoneEmails = maybeMap((demographics: PhoneEmailDemographic[]) => ({
    phoneEmails: demographics.map(asPhoneEmail)
}));
const asPhoneEmail = (demographic: PhoneEmailDemographic) => ({
    ...demographic
});

const asIdentifications = maybeMap((demographics: IdentificationDemographic[]) => ({
    identifications: demographics.map(asIdentification)
}));
const asIdentification = (demographic: IdentificationDemographic) => ({
    ...demographic
});

const asRaces = maybeMap((demographics: RaceDemographic[]) => ({
    races: demographics.map(asRace)
}));
const asRace = (demographic: RaceDemographic) => ({
    ...demographic,
    asOf: internalizeDate(demographic.asOf)
});

const asEthnicity = maybeMap((demographic: EthnicityDemographic) => ({
    ethnicity: { ...demographic, asOf: internalizeDate(demographic.asOf) }
}));

const asSexBirth = maybeMap((demographic: SexBirthDemographic) => ({
    sexBirth: { ...demographic, asOf: internalizeDate(demographic.asOf), bornOn: internalizeDate(demographic.bornOn) }
}));

const asMortality = maybeMap((demographic: MortalityDemographic) => ({
    mortality: {
        ...demographic,
        asOf: internalizeDate(demographic.asOf),
        deceasedOn: internalizeDate(demographic.deceasedOn)
    }
}));

const asGeneralInformation = maybeMap((demographic: GeneralInformationDemographic) => ({
    general: { ...demographic, asOf: internalizeDate(demographic.asOf) }
}));
