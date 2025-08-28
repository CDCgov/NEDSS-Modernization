import { asValue } from 'options';
import { orUndefined } from 'utils';
import { isEmpty } from 'utils/isEmpty';
import { GeneralInformationDemographic } from '../general';
import { GeneralInformationDemographicRequest } from './generalRequest';
import { isAllowed, Sensitive } from 'libs/sensitive';

const asValueIfAllowed = (sensitive?: Sensitive<string> | null) => {
    if (isAllowed(sensitive) && sensitive.value) {
        return sensitive.value;
    }
};

const asGeneral = (
    demographic: Partial<GeneralInformationDemographic>
): GeneralInformationDemographicRequest | undefined => {
    const { asOf, ...remaining } = demographic;

    if (asOf && !isEmpty(remaining)) {
        const {
            maritalStatus,
            primaryOccupation,
            educationLevel,
            speaksEnglish,
            primaryLanguage,
            maternalMaidenName,
            adultsInResidence,
            childrenInResidence,
            stateHIVCase
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
            stateHIVCase: asValueIfAllowed(stateHIVCase)
        };
    }
};

export { asGeneral };
