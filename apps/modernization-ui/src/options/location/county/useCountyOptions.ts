import { Selectable } from 'options/selectable';
import { selectableResolver } from 'options/selectableResolver';
import { SelectableOptionsInteraction, useSelectableOptions } from 'options/useSelectableOptions';

const resolver = (state?: string | null) => {
    return state ? selectableResolver(`/nbs/api/options/counties/${state}`) : Promise.resolve<Selectable[]>([]);
};

const useCountyOptions = (): SelectableOptionsInteraction<string> => {
    return useSelectableOptions({ resolver, lazy: true });
};

export { useCountyOptions };
