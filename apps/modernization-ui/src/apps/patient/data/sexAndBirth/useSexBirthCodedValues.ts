import { indicators } from 'coded';
import { Selectable } from 'options';
import { useConceptOptions } from 'options/concepts';
import { genders } from 'options/gender';

type SexBirthCodedValues = {
    genders: Selectable[];
    preferredGenders: Selectable[];
    genderUnknownReasons: Selectable[];
    multipleBirth: Selectable[];
};

const useSexBirthCodedValues = (): SexBirthCodedValues => {
    const preferredGenders = useConceptOptions('NBS_STD_GENDER_PARPT', { lazy: false });
    const genderUnknownReasons = useConceptOptions('SEX_UNK_REASON', { lazy: false });

    return {
        genders: genders,
        preferredGenders: preferredGenders.options,
        genderUnknownReasons: genderUnknownReasons.options,
        multipleBirth: indicators
    };
};

export { useSexBirthCodedValues };
export type { SexBirthCodedValues };
