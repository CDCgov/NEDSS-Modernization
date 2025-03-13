import { CodedValue } from 'coded';
import { useConceptOptions, ConceptNames } from 'options/concepts';

type GeneralCodedValues = {
    maritalStatuses: CodedValue[];
    educationLevels: CodedValue[];
};

const useGeneralCodedValues = (): GeneralCodedValues => {
    const maritalStatuses = useConceptOptions(ConceptNames.maritalStatuses, { lazy: false });
    const educationLevels = useConceptOptions(ConceptNames.educationLevels, { lazy: false });

    return {
        maritalStatuses: maritalStatuses.options,
        educationLevels: educationLevels.options
    };
};

export { useGeneralCodedValues };
export type { GeneralCodedValues };
