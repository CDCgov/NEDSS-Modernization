import { cachedSelectableResolver, useSelectableOptions } from 'options';

const resolver = cachedSelectableResolver('person.names.options', '/nbs/api/options/person/stdHivWorker/names');

const usePersonNamesOptions = () => {
    const { options } = useSelectableOptions({ resolver });

    return options;
};

export { usePersonNamesOptions };
