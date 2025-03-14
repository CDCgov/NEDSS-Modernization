import { CodedValue, indicators } from 'coded';
import { useConceptOptions, ConceptNames } from 'options/concepts';
import { genders } from 'options/gender';

type SexBirthCodedValues = {
    genders: CodedValue[];
    preferredGenders: CodedValue[];
    genderUnknownReasons: CodedValue[];
    multipleBirth: CodedValue[];
};

const useSexBirthCodedValues = (): SexBirthCodedValues => {
    const preferredGenders = useConceptOptions(ConceptNames.preferredGenders, { lazy: false });
    const genderUnknownReasons = useConceptOptions(ConceptNames.genderUnknownReasons, { lazy: false });

    return {
        genders: genders,
        preferredGenders: preferredGenders.options,
        genderUnknownReasons: genderUnknownReasons.options,
        multipleBirth: indicators
    };
};

export { useSexBirthCodedValues };
export type { SexBirthCodedValues };
