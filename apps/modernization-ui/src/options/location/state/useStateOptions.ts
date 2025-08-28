import { cachedSelectableResolver, useSelectableOptions } from 'options';

const resolver = cachedSelectableResolver('state.options', '/nbs/api/options/states');

const useStateOptions = () => {
    const { options } = useSelectableOptions({ resolver });

    return options;
};

export { useStateOptions };
