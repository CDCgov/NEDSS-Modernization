import { AdministrativeInformation, PatientDemographics } from 'libs/patient/demographics';
import { EthnicityDemographic, initial as initialEthnicity } from 'libs/patient/demographics/ethnicity';
import { MortalityDemographic, initial as initialMortality } from 'libs/patient/demographics/mortality';
import { SexBirthDemographic, initial as initialSexBirth } from 'libs/patient/demographics/sex-birth';
import { GeneralInformationDemographic, initial as initialGeneral } from 'libs/patient/demographics/general';
import { PatientDemographicsData } from '../demographics';
import { internalizeDate, today } from 'date';
import { mapOr, Mapping, maybeMapAll } from 'utils/mapping';
import { EffectiveDated } from 'utils';

const into =
    <P extends string, R, S>(property: P, mapping: Mapping<R, S>): Mapping<R, Record<P, S>> =>
    (value) =>
        ({ [property]: mapping(value) }) as Record<P, S>;

const copy = <V extends EffectiveDated>(value: V): V => ({ ...value, asOf: internalizeDate(value.asOf) });
const copyAll = maybeMapAll(copy);

const orElseToday = mapOr((value: string) => internalizeDate(value), today);

const initial = () => ({
    names: [],
    addresses: [],
    phoneEmails: [],
    identifications: [],
    races: []
});

/**
 * Resolves all of the demographics providers combining the results into a single instance of `PatientDemographics`
 *
 * @param {PatientDemographicsData} data The demographics data for a patient.
 * @return {Promise} A Promise containing the `PatientDemographics`
 */
const evaluated = (data: PatientDemographicsData): Promise<Required<PatientDemographics>> =>
    Promise.all([
        data.administrative.get().then(asAdministrative),
        data.names.get().then(into('names', copyAll)),
        data.addresses.get().then(into('addresses', copyAll)),
        data.phoneEmail.get().then(into('phoneEmails', copyAll)),
        data.identifications.get().then(into('identifications', copyAll)),
        data.race.get().then(into('races', copyAll)),
        data.ethnicity.get().then(into('ethnicity', mapOr(asEthnicity, initialEthnicity))),
        data.sexBirth
            .get()
            .then((resolved) => into('sexBirth', mapOr(asSexBirth, initialSexBirth))(resolved.demographic)),
        data.mortality.get().then(into('mortality', mapOr(asMortality, initialMortality))),
        data.general.get().then(into('general', mapOr(asGeneralInformation, initialGeneral)))
    ]).then(
        (resolved) =>
            resolved.reduce((current, next) => ({ ...current, ...next }), initial()) as Required<PatientDemographics>
    );

export { evaluated };

const asAdministrative = (demographic: AdministrativeInformation) => ({
    administrative: { ...demographic, asOf: orElseToday(demographic.asOf) }
});

const asEthnicity = (demographic: EthnicityDemographic) => ({
    ...demographic,
    asOf: orElseToday(demographic.asOf)
});

const asSexBirth = (demographic: SexBirthDemographic) => ({
    ...demographic,
    asOf: orElseToday(demographic.asOf),
    bornOn: internalizeDate(demographic.bornOn)
});

const asMortality = (demographic: MortalityDemographic) => ({
    ...demographic,
    asOf: orElseToday(demographic.asOf),
    deceasedOn: internalizeDate(demographic.deceasedOn)
});

const asGeneralInformation = (demographic: GeneralInformationDemographic) => ({
    ...demographic,
    asOf: internalizeDate(demographic.asOf)
});
