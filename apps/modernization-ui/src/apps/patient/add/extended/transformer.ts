import {
    asAddress,
    asAdministrative,
    asName,
    asPhoneEmail,
    asIdentification,
    asRace,
    asEthnicity,
    asSex,
    asBirth,
    asMortality,
    asGeneral
} from 'apps/patient/data';
import { ExtendedNewPatientEntry } from './entry';
import { Transformer } from './useAddExtendedPatient';
import { Mapping } from 'utils';
import { NewPatient } from './api';

const maybeMap =
    <R, S>(mapping: Mapping<R, S>) =>
    (value?: R): S | undefined =>
        value ? mapping(value) : undefined;

const maybeMapAll =
    <R, S>(mapping: Mapping<R, S>) =>
    (value?: R[]): S[] =>
        value ? value.map(mapping) : [];

const asNames = maybeMapAll(asName);
const asAddresses = maybeMapAll(asAddress);
const asPhoneEmails = maybeMapAll(asPhoneEmail);
const asIdentifications = maybeMapAll(asIdentification);
const asRaces = maybeMapAll(asRace);

const mabyeAsAdministrative = maybeMap(asAdministrative);
const maybeAsEthnicity = maybeMap(asEthnicity);
const maybeAsSex = maybeMap(asSex);
const maybeBirth = maybeMap(asBirth);
const maybeMortality = maybeMap(asMortality);
const maybeGeneral = maybeMap(asGeneral);

const transformer: Transformer = (entry: ExtendedNewPatientEntry): NewPatient => {
    const administrative = mabyeAsAdministrative(entry.administrative);
    const names = asNames(entry.names);
    const addresses = asAddresses(entry.addresses);
    const phoneEmails = asPhoneEmails(entry.phoneEmails);
    const identifications = asIdentifications(entry.identifications);
    const races = asRaces(entry.races);

    const ethnicity = maybeAsEthnicity(entry.ethnicity);
    const gender = maybeAsSex(entry.birthAndSex);
    const birth = maybeBirth(entry.birthAndSex);
    const mortality = maybeMortality(entry.mortality);
    const general = maybeGeneral(entry.general);

    return {
        administrative,
        names,
        addresses,
        phoneEmails,
        identifications,
        races,
        ethnicity,
        gender,
        birth,
        mortality,
        general
    };
};

export { transformer };
