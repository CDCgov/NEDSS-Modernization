import { CodedValue } from 'coded';
import { useConceptOptions, ConceptNames } from 'options/concepts';

type IdentificationCodedValues = {
    types: CodedValue[];
    authorities: CodedValue[];
};

const useIdentificationCodedValues = () => {
    const types = useConceptOptions(ConceptNames.identificationTypes, { lazy: false });
    const authorities = useConceptOptions(ConceptNames.assigningAuthorities, { lazy: false });

    return {
        types: types.options,
        authorities: authorities.options
    };
};

export { useIdentificationCodedValues };
export type { IdentificationCodedValues };
