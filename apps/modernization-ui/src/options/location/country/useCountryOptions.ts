import { CountriesListControllerService } from 'generated';
import { Selectable } from 'options/selectable';
import { useSelectableOptions } from 'options/useSelectableOptions';

const resolver = () => CountriesListControllerService.addressTypes();

const useCountryOptions = (): Selectable[] => {
    const { options } = useSelectableOptions({ resolver, lazy: false });

    return options;
};

export { useCountryOptions };
