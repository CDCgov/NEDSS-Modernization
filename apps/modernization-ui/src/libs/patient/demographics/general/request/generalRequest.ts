import { EffectiveDated, HasComments } from 'utils';

type GeneralInformationDemographicRequest = EffectiveDated &
    HasComments & {
        maritalStatus?: string;
        maternalMaidenName?: string;
        adultsInResidence?: number;
        childrenInResidence?: number;
        primaryOccupation?: string;
        educationLevel?: string;
        primaryLanguage?: string;
        speaksEnglish?: string;
        stateHIVCase?: string;
    };

export type { GeneralInformationDemographicRequest };
