import { Selectable } from 'options';
import { useConceptOptions } from 'options/concepts';
import { Genders, genders } from 'options/gender';
import { Indicators, indicators } from 'options/indicator';

type SexBirthOptions = {
    genders: Genders;
    preferredGenders: Selectable[];
    genderUnknownReasons: Selectable[];
    multipleBirth: Indicators;
};

const useSexBirthOptions = (): SexBirthOptions => {
    const preferredGenders = useConceptOptions('NBS_STD_GENDER_PARPT', { lazy: false });
    const genderUnknownReasons = useConceptOptions('SEX_UNK_REASON', { lazy: false });

    return {
        genders: genders,
        preferredGenders: preferredGenders.options,
        genderUnknownReasons: genderUnknownReasons.options,
        multipleBirth: indicators
    };
};

export { useSexBirthOptions };
export type { SexBirthOptions };
