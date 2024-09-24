import { asValue } from 'options';
import { GeneralInformation } from '../api';
import { GeneralInformationEntry } from '../entry';

const asGeneral = (entry: GeneralInformationEntry): GeneralInformation => {
    const { maritalStatus, primaryOccupation, educationLevel, speaksEnglish, ...remaining } = entry;

    return {
        maritalStatus: asValue(maritalStatus),
        primaryOccupation: asValue(primaryOccupation),
        educationLevel: asValue(educationLevel),
        speaksEnglish: asValue(speaksEnglish),
        ...remaining
    };
};

export { asGeneral };
