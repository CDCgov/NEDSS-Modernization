import { Selectable } from 'options';
import { useConceptOptions } from 'options/concepts';

type PhoneEmailOptions = {
    types: Selectable[];
    uses: Selectable[];
};

const usePhoneEmailOptions = (): PhoneEmailOptions => {
    const types = useConceptOptions('EL_TYPE_TELE_PAT', { lazy: false });
    const uses = useConceptOptions('EL_USE_TELE_PAT', { lazy: false });

    return {
        types: types.options,
        uses: uses.options
    };
};

export { usePhoneEmailOptions };
export type { PhoneEmailOptions };
