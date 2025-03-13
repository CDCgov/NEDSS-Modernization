import { OccupationOptionsService } from 'generated';
import { useSelectableOptions } from 'options/useSelectableOptions';

const resolver = () => OccupationOptionsService.occupations();

const useOccupationOptions = (settings = { lazy: false }) => useSelectableOptions({ ...settings, resolver });

export { useOccupationOptions };
