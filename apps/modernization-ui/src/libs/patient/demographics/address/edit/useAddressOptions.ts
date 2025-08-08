import { Selectable } from 'options';
import { useConceptOptions } from 'options/concepts';

type AddressOptions = {
    types: Selectable[];
    uses: Selectable[];
};

const useAddressOptions = (): AddressOptions => {
    const types = useConceptOptions('EL_TYPE_PST_PAT', { lazy: false });
    const uses = useConceptOptions('EL_USE_PST_PAT', { lazy: false });

    return {
        types: types.options,
        uses: uses.options
    };
};

export { useAddressOptions };
export type { AddressOptions };
