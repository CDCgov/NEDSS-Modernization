import { useConceptOptions } from 'options/concepts';
import { Selectable } from 'options/selectable';

type Interaction = {
    categories: Selectable[];
};

const useRaceCategoryOptions = (isMultiRace = false): Interaction => {
    const { options: categories } = useConceptOptions(isMultiRace ? 'RACE_CALCULATED' : 'P_RACE_CAT', { lazy: false });

    return { categories };
};

export { useRaceCategoryOptions };
