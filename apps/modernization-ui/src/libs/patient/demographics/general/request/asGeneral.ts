import { asValue } from 'options';
import { isEmpty } from 'utils/isEmpty';
import { GeneralInformationDemographic } from '../general';
import { GeneralInformationDemographicRequest } from './generalRequest';
import { isAllowed, Sensitive } from 'libs/sensitive';

const asValueIdAllowed = (sensitive?: Sensitive<string>) => {
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
            maternalMaidenName,
            adultsInResidence,
            childrenInResidence,
            stateHIVCase: asValueIdAllowed(stateHIVCase)
        };
    }
};

export { asGeneral };
