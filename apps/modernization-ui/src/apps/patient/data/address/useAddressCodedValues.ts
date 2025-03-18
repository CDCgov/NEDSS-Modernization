import { Selectable } from 'options';
import { useConceptOptions } from 'options/concepts';

type AddressCodedValues = {
    types: Selectable[];
    uses: Selectable[];
};

const useAddressCodedValues = (): AddressCodedValues => {
    const types = useConceptOptions('EL_TYPE_PST_PAT', { lazy: false });
    const uses = useConceptOptions('EL_USE_PST_PAT', { lazy: false });

    return {
        types: types.options,
        uses: uses.options
    };
};

export { useAddressCodedValues };
export type { AddressCodedValues };
