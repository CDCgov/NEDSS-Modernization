import { cachedSelectableResolver, useSelectableOptions } from 'options';

const resolver = cachedSelectableResolver('person.names.options', '/nbs/api/options/person/std-hiv-worker/names');

const useStdHivWorkerNameOptions = () => {
    const { options } = useSelectableOptions({ resolver });

    return options;
};

export { useStdHivWorkerNameOptions };
