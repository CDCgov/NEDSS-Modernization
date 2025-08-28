import { Selectable } from 'options';
import { Indicators, indicators } from 'options/indicator';
import { useConceptOptions } from 'options/concepts';
import { usePrimaryLanguageOptions } from 'options/language';
import { useOccupationOptions } from 'options/occupations';

type GeneralInformationOptions = {
    maritalStatuses: Selectable[];
    educationLevels: Selectable[];
    primaryLanguages: Selectable[];
    primaryOccupations: Selectable[];
    speaksEnglish: Indicators;
};

const useGeneralInformationOptions = (): GeneralInformationOptions => {
    const maritalStatuses = useConceptOptions('P_MARITAL');
    const educationLevels = useConceptOptions('P_EDUC_LVL');
    const primaryLanguages = usePrimaryLanguageOptions();
    const primaryOccupations = useOccupationOptions();

    return {
        maritalStatuses: maritalStatuses.options,
        educationLevels: educationLevels.options,
        primaryLanguages: primaryLanguages.options,
        primaryOccupations: primaryOccupations.options,
        speaksEnglish: indicators
    };
};

export { useGeneralInformationOptions };
export type { GeneralInformationOptions };
