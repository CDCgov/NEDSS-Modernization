import { CodedValue } from 'coded';
import { useConceptOptions, ConceptNames } from 'options/concepts';

type AddressCodedValues = {
    types: CodedValue[];
    uses: CodedValue[];
};

const useAddressCodedValues = () => {
    const types = useConceptOptions(ConceptNames.addressTypes, { lazy: false });
    const uses = useConceptOptions(ConceptNames.addressUses, { lazy: false });

    return {
        types: types.options,
        uses: uses.options
    };
};

export { useAddressCodedValues };
export type { AddressCodedValues };
