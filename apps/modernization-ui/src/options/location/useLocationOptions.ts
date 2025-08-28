import { useCallback } from 'react';
import { Selectable } from 'options/selectable';
import { useStateOptions } from './state/useStateOptions';
import { useCountryOptions } from './country/useCountryOptions';
import { useCountyOptions } from './county/useCountyOptions';

type LocationOptions = {
    states: Selectable[];
    state: (selected?: Selectable | null) => void;
    counties: Selectable[];
    countries: Selectable[];
};

const useLocationOptions = (): LocationOptions => {
    const states = useStateOptions();
    const { options: counties, load } = useCountyOptions();
    const countries = useCountryOptions();

    const state = useCallback((selected?: Selectable | null) => load(selected?.value), [load]);

    return { states, state, counties, countries };
};

export { useLocationOptions };
export type { LocationOptions };
