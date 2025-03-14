import { PrimaryLanguageOptionsService } from 'generated';
import { useSelectableOptions } from 'options/useSelectableOptions';

const resolver = () => PrimaryLanguageOptionsService.primaryLanguages();

const usePrimaryLanguageOptions = (settings = { lazy: false }) => useSelectableOptions({ ...settings, resolver });

export { usePrimaryLanguageOptions };
