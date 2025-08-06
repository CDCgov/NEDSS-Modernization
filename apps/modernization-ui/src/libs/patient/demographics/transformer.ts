import { maybeMap, maybeMapAll } from 'utils/mapping';
import { PatientDemographics } from './demographics';
import { PatientDemographicsRequest } from './request';
import { asAdministrative } from './administrative';
import { asName } from './name';
import { asAddress } from './address';
import { asEthnicity } from './ethnicity';
import { asGeneral } from './general';
import { asIdentification } from './identification';
import { asMortality } from './mortality';
import { asPhoneEmail } from './phoneEmail';
import { asRace } from './race';
import { asBirth, asSex } from './sex-birth';

const asNames = maybeMapAll(asName);
const asAddresses = maybeMapAll(asAddress);
const asPhoneEmails = maybeMapAll(asPhoneEmail);
const asIdentifications = maybeMapAll(asIdentification);
const asRaces = maybeMapAll(asRace);

const maybeAsAdministrative = maybeMap(asAdministrative);
const maybeAsEthnicity = maybeMap(asEthnicity);
const maybeAsSex = maybeMap(asSex);
const maybeBirth = maybeMap(asBirth);
const maybeMortality = maybeMap(asMortality);
const maybeGeneral = maybeMap(asGeneral);

const transformer = (demographics: PatientDemographics): PatientDemographicsRequest => {
    const administrative = maybeAsAdministrative(demographics.administrative);
    const names = asNames(demographics.names);
    const addresses = asAddresses(demographics.addresses);
    const phoneEmails = asPhoneEmails(demographics.phoneEmails);
    const identifications = asIdentifications(demographics.identifications);
    const races = asRaces(demographics.races);

    const ethnicity = maybeAsEthnicity(demographics.ethnicity);
    const gender = maybeAsSex(demographics.sexBirth);
    const birth = maybeBirth(demographics.sexBirth);
    const mortality = maybeMortality(demographics.mortality);
    const general = maybeGeneral(demographics.general);

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
