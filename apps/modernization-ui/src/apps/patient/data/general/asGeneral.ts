import { asValue } from 'options';
import { GeneralInformation } from '../api';
import { GeneralInformationEntry } from '../entry';

const asGeneral = (entry: GeneralInformationEntry): GeneralInformation => {
    const {
        asOf,
        maternalMaidenName,
        adultsInResidence,
        childrenInResidence,
        maritalStatus,
        primaryOccupation,
        educationLevel,
        speaksEnglish,
        primaryLanguage,
        stateHIVCase
    } = entry;

    return {
        asOf,
        maternalMaidenName,
        adultsInResidence,
        childrenInResidence,
        maritalStatus: asValue(maritalStatus),
        primaryOccupation: asValue(primaryOccupation),
        educationLevel: asValue(educationLevel),
        speaksEnglish: asValue(speaksEnglish),
        primaryLanguage: asValue(primaryLanguage),
        stateHIVCase
    };
};

export { asGeneral };
