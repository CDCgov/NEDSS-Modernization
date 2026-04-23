import { cachedSelectableResolver, useSelectableOptions } from 'options';

const resolver = cachedSelectableResolver('region.options', '/nbs/api/options/regions');

const useRegionOptions = () => {
    const { options } = useSelectableOptions({ resolver });

    return options;
};

export { useRegionOptions };
