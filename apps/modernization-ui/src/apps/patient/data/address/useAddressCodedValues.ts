import { Selectable } from 'options';
import { useConceptOptions } from 'options/concepts';
import { useMemo } from 'react';

type AddressCodedValues = {
    types: Selectable[];
    uses: Selectable[];
};

const useAddressCodedValues = (): AddressCodedValues => {
    const types = useConceptOptions('EL_TYPE_PST_PAT', { lazy: false });
    const uses = useConceptOptions('EL_USE_PST_PAT', { lazy: false });

    const filteredUses = useMemo(() => {
        return uses.options.filter((option) => option.value !== 'BIR' && option.value !== 'DTH');
    }, [uses.options]);

    return {
        types: types.options,
        uses: filteredUses
    };
};

export { useAddressCodedValues };
export type { AddressCodedValues };
