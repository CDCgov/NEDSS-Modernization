import { PatientDemographics, PatientDemographicsEntry } from 'libs/patient/demographics';
import { AdministrativeInformation, initial as initialAdministrative } from 'libs/patient/demographics/administrative';
import { EthnicityDemographic, initial as initialEthnicity } from 'libs/patient/demographics/ethnicity';
import { MortalityDemographic, initial as initialMortality } from 'libs/patient/demographics/mortality';
import { SexBirthDemographic, initial as initialSexBirth } from 'libs/patient/demographics/sex-birth';
import { GeneralInformationDemographic, initial as initialGeneral } from 'libs/patient/demographics/general';
import { PatientDemographicsData } from '../demographics';
import { internalizeDate, today } from 'date';
import { mapOr, Mapping, maybeMapAll } from 'utils/mapping';
import { EffectiveDated } from 'utils';
import { Supplier } from 'libs/supplying';

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
    races: [],
    pending: []
});

/**
 * Resolves all of the demographics providers combining the results into a single instance of `PatientDemographics`
 *
 * @param {PatientDemographicsData} data The demographics data for a patient.
 * @return {Promise} A Promise containing the `PatientDemographics`
 */
const evaluated = (data: PatientDemographicsData): Promise<PatientDemographicsEntry> =>
    Promise.all([
        data.administrative.get().then(evaluateAdministrative),
        data.names.get().then(into('names', copyAll)),
        data.addresses.get().then(into('addresses', copyAll)),
        data.phoneEmail.get().then(into('phoneEmails', copyAll)),
        data.identifications.get().then(into('identifications', copyAll)),
        data.race.get().then(into('races', copyAll)),
        data.ethnicity.get().then(evaluateEthnicity),
        data.sexBirth.get().then((resolved) => evaluateSexBirth(resolved.demographic)),
        data.mortality.get().then(evaluateMortality),
        data.general.get().then(evaluateGeneralInformation)
    ]).then(
        (resolved) =>
            resolved.reduce((current, next) => ({ ...current, ...next }), initial()) as Required<PatientDemographics>
    );

export { evaluated };

const asAdministrative = (demographic: Partial<AdministrativeInformation>) => ({
    ...demographic,
    asOf: orElseToday(demographic.asOf)
});

const asOfToday =
    <E extends EffectiveDated>(fn: (supplier: Supplier<string>) => E) =>
    () =>
        fn(today);

const evaluateAdministrative = into('administrative', mapOr(asAdministrative, asOfToday(initialAdministrative)));

const asEthnicity = (demographic: Partial<EthnicityDemographic>) => ({
    ...demographic,
    asOf: orElseToday(demographic.asOf)
});

const evaluateEthnicity = into('ethnicity', mapOr(asEthnicity, asOfToday(initialEthnicity)));

const asSexBirth = (demographic: Partial<SexBirthDemographic>) => ({
    ...demographic,
    asOf: orElseToday(demographic.asOf),
    bornOn: internalizeDate(demographic.bornOn)
});

const evaluateSexBirth = into('sexBirth', mapOr(asSexBirth, asOfToday(initialSexBirth)));

const asMortality = (demographic: Partial<MortalityDemographic>) => ({
    ...demographic,
    asOf: orElseToday(demographic.asOf),
    deceasedOn: internalizeDate(demographic.deceasedOn)
});

const evaluateMortality = into('mortality', mapOr(asMortality, asOfToday(initialMortality)));

const asGeneralInformation = (demographic: Partial<GeneralInformationDemographic>) => ({
    ...demographic,
    asOf: internalizeDate(demographic.asOf)
});

const evaluateGeneralInformation = into('general', mapOr(asGeneralInformation, asOfToday(initialGeneral)));

export { evaluateAdministrative, evaluateEthnicity, evaluateSexBirth, evaluateMortality, evaluateGeneralInformation };
