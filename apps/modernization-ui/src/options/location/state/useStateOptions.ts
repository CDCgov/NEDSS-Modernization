import { StatesOptionsService } from 'generated';
import { Selectable } from 'options';
import { useSelectableOptions } from 'options/useSelectableOptions';

const resolver = () => StatesOptionsService.states();

const useStateOptions = (): Selectable[] => {
    const { options } = useSelectableOptions({ resolver, lazy: false });

    return options;
};

export { useStateOptions };
