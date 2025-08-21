import { addDays } from 'date-fns';
import { get, maybeJson } from 'libs/api';
import { Selectable } from 'options/selectable';
import { cache } from 'options/cached';
import { useSelectableOptions } from 'options/useSelectableOptions';

const expiration = () => addDays(new Date(), 1);

type ConceptOptions = {
    options: Selectable[];
    load: () => void;
};

type Settings = {
    lazy?: boolean;
};

const resolver = (name: string) => () =>
    cache<Selectable[]>({ id: `concept.options.${name}`, expiration, storage: localStorage })(() =>
        fetch(get(`/nbs/api/options/concepts/${name}`))
            .then(maybeJson)
            .then((response) => response.options)
    );

const useConceptOptions = (valueSet: string, { lazy = false }: Settings): ConceptOptions => {
    return useSelectableOptions({ resolver: resolver(valueSet), lazy });
};

export { useConceptOptions };
