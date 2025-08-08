import { today } from 'date';
import { Sensitive } from 'libs/sensitive';
import { Selectable } from 'options';

type GeneralInformationDemographic = {
    asOf?: string;
    maritalStatus?: Selectable;
    maternalMaidenName?: string;
    adultsInResidence?: number;
    childrenInResidence?: number;
    primaryOccupation?: Selectable;
    educationLevel?: Selectable;
    primaryLanguage?: Selectable;
    speaksEnglish?: Selectable;
    stateHIVCase?: Sensitive<string>;
};

type HasGeneralInformationDemographic = { general?: GeneralInformationDemographic };

export type { GeneralInformationDemographic, HasGeneralInformationDemographic };

const initial = (asOf: string = today()): GeneralInformationDemographic => ({
    asOf: asOf,
    maritalStatus: undefined,
    maternalMaidenName: undefined,
    adultsInResidence: undefined,
    childrenInResidence: undefined,
    primaryOccupation: undefined,
    educationLevel: undefined,
    primaryLanguage: undefined,
    speaksEnglish: undefined,
    stateHIVCase: undefined
});

export { initial };

const labels = {
    asOf: 'As of',
    maritalStatus: 'Marital status',
    maternalMaidenName: "Mother's maiden name",
    adultsInResidence: 'Number of adults in residence',
    childrenInResidence: 'Number of children in residence',
    primaryOccupation: 'Primary occupation',
    educationLevel: 'Highest level of education',
    primaryLanguage: 'Primary language',
    speaksEnglish: 'Speaks English',
    stateHIVCase: 'State HIV case ID'
};

export { labels };
