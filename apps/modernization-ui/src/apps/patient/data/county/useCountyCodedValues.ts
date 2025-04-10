import { CountyOptionsService } from 'generated';
import { Selectable } from 'options/selectable';
import { useSelectableOptions } from 'options/useSelectableOptions';

type CountyCodedValues = {
    options: Selectable[];
    load: () => void;
};

const resolver = (state: string) => () => {
    return state
        ? CountyOptionsService.countyAutocomplete({
              criteria: '',
              state: state,
              limit: 100000
          })
        : Promise.resolve([]);
};

const useCountyCodedValues = (state: string): CountyCodedValues => {
    return useSelectableOptions({ resolver: resolver(state), lazy: false });
};

export { useCountyCodedValues };
