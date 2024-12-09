import { useConceptOptions } from 'options/concepts';
import { Selectable } from 'options/selectable';

type Interaction = {
    categories: Selectable[];
};

const useRaceCategoryOptions = (): Interaction => {
    const { options: categories } = useConceptOptions( 'RACE_CALCULATED', { lazy: false });

    return { categories };
};

export { useRaceCategoryOptions };
