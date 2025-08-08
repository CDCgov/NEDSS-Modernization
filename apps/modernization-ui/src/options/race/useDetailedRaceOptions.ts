import { Selectable, useSelectableOptions, SelectableOptionsInteraction } from 'options';
import { RaceOptionsService } from 'generated';

const resolver = (category?: string) =>
    category
        ? RaceOptionsService.detailedRaces({ category }).then((options) => options as Selectable[])
        : Promise.resolve<Selectable[]>([]);

const useDetailedRaceOptions = (): SelectableOptionsInteraction<string> => {
    return useSelectableOptions({ resolver, lazy: true });
};

export { useDetailedRaceOptions };
