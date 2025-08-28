import { cachedSelectableResolver, useSelectableOptions } from 'options';

const resolver = cachedSelectableResolver('occupations.options', '/nbs/api/options/occupations');

const useOccupationOptions = () => useSelectableOptions({ resolver });

export { useOccupationOptions };
