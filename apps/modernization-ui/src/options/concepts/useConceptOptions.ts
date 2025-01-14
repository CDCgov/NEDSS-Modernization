import { ConceptOptionsService } from 'generated';
import { Selectable } from 'options/selectable';
import { useSelectableOptions } from 'options/useSelectableOptions';

type ConceptOptions = {
    options: Selectable[];
    load: () => void;
};

type Settings = {
    lazy?: boolean;
};

const resolver = (valueSet: string) => () =>
    ConceptOptionsService.concepts({
        name: valueSet
    }).then((response) => response.options);

const useConceptOptions = (valueSet: string, { lazy = true }: Settings): ConceptOptions => {
    return useSelectableOptions({ resolver: resolver(valueSet), lazy });
};

export { useConceptOptions };
