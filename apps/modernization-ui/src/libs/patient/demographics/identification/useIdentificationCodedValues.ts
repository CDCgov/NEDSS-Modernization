import { Selectable } from 'options';
import { useConceptOptions } from 'options/concepts';

type IdentificationCodedValues = {
    types: Selectable[];
    authorities: Selectable[];
};

const useIdentificationCodedValues = () => {
    const types = useConceptOptions('EI_TYPE_PAT', { lazy: false });
    const authorities = useConceptOptions('EI_AUTH_PAT', { lazy: false });

    return {
        types: types.options,
        authorities: authorities.options
    };
};

export { useIdentificationCodedValues };
export type { IdentificationCodedValues };
