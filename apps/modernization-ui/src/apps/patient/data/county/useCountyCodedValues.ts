import { CountyOptionsService } from 'generated';
import { Selectable } from 'options/selectable';
import { useSelectableOptions } from 'options/useSelectableOptions';
import { useMemo } from 'react';

type CountyCodedValues = {
    options: Selectable[];
    load: () => void;
};

type Settings = {
    lazy?: boolean;
};

const resolver = (state: string) => () =>
    CountyOptionsService.countyAutocomplete({ criteria: '', state: state, limit: 100000 });

const useCountyCodedValues = (state: string, { lazy = true }: Settings): CountyCodedValues => {
    const cachedResolver = useMemo(() => resolver(state), []);
    return useSelectableOptions({ resolver: cachedResolver, lazy });
};

export { useCountyCodedValues };
