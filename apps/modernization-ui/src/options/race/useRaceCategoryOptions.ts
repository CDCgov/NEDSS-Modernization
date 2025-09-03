import { Selectable, cachedSelectableResolver, useSelectableOptions } from 'options';
import { Predicate } from 'utils';

const resolver = cachedSelectableResolver('races.options', '/nbs/api/options/races');

type Settings = {
    filter?: Predicate<Selectable>;
};

const useRaceCategoryOptions = (settings?: Settings): Selectable[] => {
    const { options } = useSelectableOptions({ resolver });

    const categories = settings?.filter ? options.filter(settings.filter) : options;

    return categories;
};

export { useRaceCategoryOptions };
