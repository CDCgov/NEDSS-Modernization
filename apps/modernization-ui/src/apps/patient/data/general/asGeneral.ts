import { asValue } from 'options';
import { orUndefined } from 'utils';
import { isEmpty } from 'utils/isEmpty';

import { GeneralInformation } from '../api';
import { GeneralInformationEntry } from '../entry';

const asGeneral = (entry: GeneralInformationEntry): GeneralInformation | undefined => {
    const { asOf, ...remaining } = entry;

    if (!isEmpty(remaining)) {
        const {
            maritalStatus,
            primaryOccupation,
            educationLevel,
            speaksEnglish,
            primaryLanguage,
            maternalMaidenName,
            adultsInResidence,
            childrenInResidence,
            stateHIVCase,
        } = remaining;

        return {
            asOf,
            maritalStatus: asValue(maritalStatus),
            primaryOccupation: asValue(primaryOccupation),
            educationLevel: asValue(educationLevel),
            speaksEnglish: asValue(speaksEnglish),
            primaryLanguage: asValue(primaryLanguage),
            maternalMaidenName: orUndefined(maternalMaidenName),
            adultsInResidence: orUndefined(adultsInResidence),
            childrenInResidence: orUndefined(childrenInResidence),
            stateHIVCase: orUndefined(stateHIVCase),
        };
    }
};

export { asGeneral };
