import { Selectable } from 'options';
import { useConceptOptions } from 'options/concepts';
import { Genders, genders } from 'options/gender';
import { Indicators, indicators } from 'options/indicator';
import { LocationOptions, useLocationOptions } from 'options/location';

type SexBirthOptions = {
    genders: Genders;
    preferredGenders: Selectable[];
    genderUnknownReasons: Selectable[];
    multipleBirth: Indicators;
    location: LocationOptions;
};

const useSexBirthOptions = (): SexBirthOptions => {
    const preferredGenders = useConceptOptions('NBS_STD_GENDER_PARPT', { lazy: false });
    const genderUnknownReasons = useConceptOptions('SEX_UNK_REASON', { lazy: false });
    const location = useLocationOptions();

    return {
        genders: genders,
        preferredGenders: preferredGenders.options,
        genderUnknownReasons: genderUnknownReasons.options,
        multipleBirth: indicators,
        location
    };
};

export { useSexBirthOptions };
export type { SexBirthOptions };
