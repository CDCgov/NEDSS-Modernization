import { cachedSelectableResolver } from 'options/cache/cachedSelectableResolver';
import { Selectable } from 'options/selectable';
import { SelectableOptionsInteraction, useSelectableOptions } from 'options/useSelectableOptions';

const resolver = (state?: string | null) => {
    return state
        ? cachedSelectableResolver(`county.options.${state}`, `/nbs/api/options/counties/${state}`)()
        : Promise.resolve<Selectable[]>([]);
};

const useCountyOptions = (): SelectableOptionsInteraction<string> => {
    return useSelectableOptions({ resolver, lazy: true });
};

export { useCountyOptions };
