import { useEffect } from 'react';
import { Selectable, useSelectableOptions } from 'options';
import { RaceOptionsService } from 'generated';

const resolver = (category?: string) => () =>
    category
        ? RaceOptionsService.detailedRaces({ category }).then((options) => options as Selectable[])
        : Promise.resolve<Selectable[]>([]);

const useDetailedRaceOptions = (category?: string): Selectable[] => {
    const { options: detailedRaces, load } = useSelectableOptions({ resolver: resolver(category), lazy: true });

    useEffect(() => {
        load();
    }, [category]);

    return detailedRaces;
};

export { useDetailedRaceOptions };
