import { cachedSelectableResolver, useSelectableOptions } from 'options';

const resolver = cachedSelectableResolver('condition.options', '/nbs/api/options/conditions');

const useConditionOptions = () => {
    const { options } = useSelectableOptions({ resolver });
    return options;
}

export { useConditionOptions };
