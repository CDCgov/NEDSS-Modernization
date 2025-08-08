import { Selectable } from 'options';
import { useConceptOptions } from 'options/concepts';

type IdentificationOptions = {
    types: Selectable[];
    authorities: Selectable[];
};

const useIdentificationOptions = () => {
    const types = useConceptOptions('EI_TYPE_PAT', { lazy: false });
    const authorities = useConceptOptions('EI_AUTH_PAT', { lazy: false });

    return {
        types: types.options,
        authorities: authorities.options
    };
};

export { useIdentificationOptions };
export type { IdentificationOptions };
