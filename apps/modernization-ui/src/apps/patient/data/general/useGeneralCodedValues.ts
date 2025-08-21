import { Selectable } from 'options';
import { useConceptOptions } from 'options/concepts';
import { indicators } from 'options/indicator';
import { usePrimaryLanguageOptions } from 'options/language';
import { useOccupationOptions } from 'options/occupations';

type GeneralCodedValues = {
    maritalStatuses: Selectable[];
    educationLevels: Selectable[];
    primaryLanguages: Selectable[];
    primaryOccupations: Selectable[];
    speaksEnglish: Selectable[];
};

const useGeneralCodedValues = (): GeneralCodedValues => {
    const maritalStatuses = useConceptOptions('P_MARITAL');
    const educationLevels = useConceptOptions('P_EDUC_LVL');
    const primaryLanguages = usePrimaryLanguageOptions();
    const primaryOccupations = useOccupationOptions();

    return {
        maritalStatuses: maritalStatuses.options,
        educationLevels: educationLevels.options,
        primaryLanguages: primaryLanguages.options,
        primaryOccupations: primaryOccupations.options,
        speaksEnglish: indicators.all
    };
};

export { useGeneralCodedValues };
export type { GeneralCodedValues };
