import { RaceOptionsService } from 'generated';

import { Selectable, useSelectableOptions } from 'options';
import { Predicate } from 'utils';

const resolver = () => RaceOptionsService.races().then((options) => options as Selectable[]);

type Settings = {
    filter?: Predicate<Selectable>;
};

const useRaceCategoryOptions = (settings?: Settings): Selectable[] => {
    const { options } = useSelectableOptions({ resolver, lazy: false });

    const categories = settings?.filter ? options.filter(settings.filter) : options;

    return categories;
};

export { useRaceCategoryOptions };
