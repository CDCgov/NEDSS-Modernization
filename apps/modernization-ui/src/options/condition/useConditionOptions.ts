import { cachedSelectableResolver, useSelectableOptions } from 'options';

const resolver = cachedSelectableResolver('condition.options', '/nbs/api/options/conditions');

const useConditionOptions = () => useSelectableOptions({ resolver });

export { useConditionOptions };
