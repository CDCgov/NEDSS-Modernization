import { asValue } from 'options';
import { GeneralInformation } from '../api';
import { GeneralInformationEntry } from '../entry';

const asGeneral = (entry: GeneralInformationEntry): GeneralInformation => {
    const { maritalStatus, primaryOccupation, educationLevel, speaksEnglish, primaryLanguage, ...remaining } = entry;

    console.log(remaining);

    return {
        maritalStatus: asValue(maritalStatus),
        primaryOccupation: asValue(primaryOccupation),
        educationLevel: asValue(educationLevel),
        speaksEnglish: asValue(speaksEnglish),
        primaryLanguage: asValue(primaryLanguage),
        ...remaining
    };
};

export { asGeneral };
