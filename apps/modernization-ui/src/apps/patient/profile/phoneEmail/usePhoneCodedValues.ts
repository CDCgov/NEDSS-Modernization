import { CodedValue } from 'coded';
import { useConceptOptions, ConceptNames } from 'options/concepts';

type PhoneCodedValues = {
    types: CodedValue[];
    uses: CodedValue[];
};

const usePhoneCodedValues = (): PhoneCodedValues => {
    const types = useConceptOptions(ConceptNames.phoneTypes, { lazy: false });
    const uses = useConceptOptions(ConceptNames.phoneUses, { lazy: false });

    return {
        types: types.options,
        uses: uses.options
    };
};

export { usePhoneCodedValues };
export type { PhoneCodedValues };
