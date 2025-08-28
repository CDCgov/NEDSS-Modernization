import { Selectable } from 'options';
import { useConceptOptions } from 'options/concepts';
import { LocationOptions, useLocationOptions } from 'options/location';

type AddressOptions = {
    types: Selectable[];
    uses: Selectable[];
    location: LocationOptions;
};

const useAddressOptions = (): AddressOptions => {
    const types = useConceptOptions('EL_TYPE_PST_PAT', { lazy: false });
    const uses = useConceptOptions('EL_USE_PST_PAT', { lazy: false });
    const location = useLocationOptions();

    return {
        types: types.options,
        uses: uses.options,
        location
    };
};

export { useAddressOptions };
export type { AddressOptions };
