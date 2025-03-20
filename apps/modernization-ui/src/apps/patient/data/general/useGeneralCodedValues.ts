import { indicators } from 'coded';
import { Selectable } from 'options';
import { useConceptOptions } from 'options/concepts';
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
    const maritalStatuses = useConceptOptions('P_MARITAL', { lazy: false });
    const educationLevels = useConceptOptions('P_EDUC_LVL', { lazy: false });
    const primaryLanguages = usePrimaryLanguageOptions({ lazy: false });
    const primaryOccupations = useOccupationOptions({ lazy: false });

    return {
        maritalStatuses: maritalStatuses.options,
        educationLevels: educationLevels.options,
        primaryLanguages: primaryLanguages.options,
        primaryOccupations: primaryOccupations.options,
        speaksEnglish: indicators
    };
};

export { useGeneralCodedValues };
export type { GeneralCodedValues };
