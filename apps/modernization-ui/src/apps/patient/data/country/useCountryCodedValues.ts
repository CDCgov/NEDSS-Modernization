import { CountriesListControllerService } from 'generated';
import { Selectable } from 'options/selectable';
import { useSelectableOptions } from 'options/useSelectableOptions';
import { useMemo } from 'react';

type CountryCodedValues = {
    options: Selectable[];
    load: () => void;
};

type Settings = {
    lazy?: boolean;
};

const resolver = () => () => CountriesListControllerService.addressTypes();

const useCountryCodedValues = ({ lazy = true }: Settings): CountryCodedValues => {
    const cachedResolver = useMemo(() => resolver(), []);
    return useSelectableOptions({ resolver: cachedResolver, lazy });
};

export { useCountryCodedValues };
