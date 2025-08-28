import { cachedSelectableResolver, useSelectableOptions } from 'options';

const resolver = cachedSelectableResolver('country.options', '/nbs/api/options/countries');

const useCountryOptions = () => {
    const { options } = useSelectableOptions({ resolver });

    return options;
};

export { useCountryOptions };
