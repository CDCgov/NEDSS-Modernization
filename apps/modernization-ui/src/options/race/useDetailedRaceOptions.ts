import { Selectable, useSelectableOptions, SelectableOptionsInteraction } from 'options';
import { selectableResolver } from 'options/selectableResolver';

const resolver = (category?: string) =>
    category ? selectableResolver(`/nbs/api/options/races/${category}`) : Promise.resolve<Selectable[]>([]);

const useDetailedRaceOptions = (): SelectableOptionsInteraction<string> => {
    return useSelectableOptions({ resolver });
};

export { useDetailedRaceOptions };
