import { useEffect, useState } from 'react';
import { ProgramAreaCode, useFindAllProgramAreasLazyQuery } from 'generated/graphql/schema';
import { Selectable } from 'options/selectable';
import { SelectableResolver, findByValue } from 'options/findByValue';
import { mapNonNull } from 'utils';

const asSelectable = (option: ProgramAreaCode): Selectable => ({
    value: option.id,
    name: option.progAreaDescTxt || '',
    label: option.progAreaDescTxt || ''
});

type Interaction = {
    all: Selectable[];
    resolve: SelectableResolver;
};

const useProgramAreaOptions = (): Interaction => {
    const [options, setOptions] = useState<Selectable[]>([]);

    const [getOptions] = useFindAllProgramAreasLazyQuery();

    useEffect(() => {
        getOptions().then((results) => {
            if (results?.data?.findAllProgramAreas) {
                const selectables = mapNonNull(asSelectable, results.data.findAllProgramAreas);
                setOptions(selectables);
            }
        });
    }, []);

    return { all: options, resolve: findByValue(options) };
};

export { useProgramAreaOptions };
