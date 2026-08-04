import { NewPatient, Transformer } from 'apps/patient/add/api';
import {
    asAddress,
    asAdministrative,
    asBirth,
    asEthnicity,
    asGeneral,
    asIdentification,
    asMortality,
    asName,
    asPhoneEmail,
    asRace,
    asSex,
} from 'apps/patient/data';
import { maybeMap, maybeMapAll } from 'utils/mapping';

import { ExtendedNewPatientEntry } from './entry';

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

const transformer: Transformer<ExtendedNewPatientEntry> = (entry: ExtendedNewPatientEntry): NewPatient => {
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
        general,
    };
};

export { transformer };
