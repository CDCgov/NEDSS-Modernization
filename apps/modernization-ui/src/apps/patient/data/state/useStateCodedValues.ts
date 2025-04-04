import { StatesOptionsService } from 'generated';
import { Selectable } from 'options/selectable';
import { useSelectableOptions } from 'options/useSelectableOptions';
import { useMemo } from 'react';

type StateCodedValues = {
    options: Selectable[];
    load: () => void;
};

type Settings = {
    lazy?: boolean;
};

const resolver = () => () => StatesOptionsService.states();

const useStateCodedValues = ({ lazy = true }: Settings): StateCodedValues => {
    const cachedResolver = useMemo(() => resolver(), []);
    return useSelectableOptions({ resolver: cachedResolver, lazy });
};

export { useStateCodedValues };
