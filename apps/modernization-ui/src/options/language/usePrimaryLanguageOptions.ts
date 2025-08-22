import { cachedSelectableResolver, useSelectableOptions } from 'options';

const resolver = cachedSelectableResolver('primary-languages.options', '/nbs/api/options/languages/primary');

const usePrimaryLanguageOptions = () => useSelectableOptions({ resolver });

export { usePrimaryLanguageOptions };
