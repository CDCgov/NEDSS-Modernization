import { CountyOptionsService } from 'generated';
import { Selectable } from 'options/selectable';
import { useSelectableOptions } from 'options/useSelectableOptions';
import { useEffect } from 'react';

const resolver = (state?: string | null) => () => {
    return state
        ? CountyOptionsService.countyAutocomplete({
              criteria: '',
              state,
              limit: 100000
          })
        : Promise.resolve([]);
};

const useCountyOptions = (state?: string | null): Selectable[] => {
    const { options, load } = useSelectableOptions({ resolver: resolver(state), lazy: true });

    useEffect(() => {
        load();
    }, [state, load]);

    return options;
};

export { useCountyOptions };
