import { useConceptOptions } from 'options/concepts';
import { Selectable } from 'options/selectable';

type Interaction = {
    categories: Selectable[];
};

const useRaceCategoryOptions = (): Interaction => {
    const { options: categories } = useConceptOptions('P_RACE_CAT', { lazy: false });

    return { categories };
};

export { useRaceCategoryOptions };
