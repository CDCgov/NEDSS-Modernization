import { StatesOptionsService } from 'generated';
import { Selectable } from 'options';
import { useSelectableOptions } from 'options/useSelectableOptions';

const resolver = () => StatesOptionsService.states();

const useStateOptions = (): Selectable[] => {
    const { options } = useSelectableOptions({ resolver, lazy: true });

    return options;
};

export { useStateOptions };
