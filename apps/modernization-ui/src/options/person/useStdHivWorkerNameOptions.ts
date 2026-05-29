import { cachedSelectableResolver, useSelectableOptions } from 'options/index.ts';

const resolver = cachedSelectableResolver('stdHivWorkerName.options', '/nbs/api/options/person/std-hiv-worker/names');

const useStdHivWorkerNameOptions = () => {
    const { options } = useSelectableOptions({ resolver });

    return options;
};

export { useStdHivWorkerNameOptions };
