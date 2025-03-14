import { CodedValue, indicators } from 'coded';
import { useConceptOptions, ConceptNames } from 'options/concepts';
import { usePrimaryLanguageOptions } from 'options/language';
import { useOccupationOptions } from 'options/occupations';

type GeneralCodedValues = {
    maritalStatuses: CodedValue[];
    educationLevels: CodedValue[];
    primaryLanguages: CodedValue[];
    primaryOccupations: CodedValue[];
    speaksEnglish: CodedValue[];
};

const useGeneralCodedValues = (): GeneralCodedValues => {
    const maritalStatuses = useConceptOptions(ConceptNames.maritalStatuses, { lazy: false });
    const educationLevels = useConceptOptions(ConceptNames.educationLevels, { lazy: false });
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
