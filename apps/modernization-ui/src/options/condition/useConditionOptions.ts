import { ConditionOptionsService } from 'generated';
import { useSelectableOptions } from 'options/useSelectableOptions';

const resolver = () => ConditionOptionsService.all();

const useConditionOptions = (settings = { lazy: false }) => useSelectableOptions({ ...settings, resolver });

export { useConditionOptions };
