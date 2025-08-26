import { Supplier } from 'libs/supplying';
import { Selectable } from 'options';
import { EffectiveDated } from 'utils';
import { Nullable } from 'utils/object';
import { Sensitive } from 'libs/sensitive';

type GeneralInformationDemographic = EffectiveDated &
    Nullable<{
        maritalStatus: Selectable;
        maternalMaidenName: string;
        adultsInResidence: number;
        childrenInResidence: number;
        primaryOccupation: Selectable;
        educationLevel: Selectable;
        primaryLanguage: Selectable;
        speaksEnglish: Selectable;
        stateHIVCase: Sensitive<string>;
    }>;

type HasGeneralInformationDemographic = { general?: GeneralInformationDemographic };

export type { GeneralInformationDemographic, HasGeneralInformationDemographic };

const initial = (asOf: Supplier<string>): GeneralInformationDemographic => ({
    asOf: asOf(),
    maritalStatus: null,
    maternalMaidenName: null,
    adultsInResidence: null,
    childrenInResidence: null,
    primaryOccupation: null,
    educationLevel: null,
    primaryLanguage: null,
    speaksEnglish: null,
    stateHIVCase: null
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
