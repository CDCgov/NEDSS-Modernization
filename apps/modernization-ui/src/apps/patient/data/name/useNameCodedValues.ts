import { Selectable } from 'options';
import { useConceptOptions } from 'options/concepts';

type NameCodedValues = {
    types: Selectable[];
    prefixes: Selectable[];
    suffixes: Selectable[];
    degrees: Selectable[];
};

const useNameCodedValues = (): NameCodedValues => {
    const types = useConceptOptions('P_NM_USE', { lazy: false });
    const prefixes = useConceptOptions('P_NM_PFX', { lazy: false });
    const suffixes = useConceptOptions('P_NM_SFX', { lazy: false });
    const degrees = useConceptOptions('P_NM_DEG', { lazy: false });

    return {
        types: types.options,
        prefixes: prefixes.options,
        suffixes: suffixes.options,
        degrees: degrees.options
    };
};

export { useNameCodedValues };
export type { NameCodedValues };
