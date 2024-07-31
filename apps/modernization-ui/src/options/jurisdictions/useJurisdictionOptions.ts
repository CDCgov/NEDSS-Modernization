import { useEffect, useState } from 'react';
import { Jurisdiction, useFindAllJurisdictionsLazyQuery } from 'generated/graphql/schema';
import { Selectable } from 'options/selectable';
import { mapNonNull } from 'utils';
import { SelectableResolver, findByValue } from 'options/findByValue';

const asSelectable = (jurisdiction: Jurisdiction): Selectable => ({
    value: jurisdiction.id,
    name: jurisdiction.codeShortDescTxt || '',
    label: jurisdiction.codeShortDescTxt || ''
});

type Interaction = {
    all: Selectable[];
    resolve: SelectableResolver;
};

const useJurisdictionOptions = (): Interaction => {
    const [jurisdictions, setJurisdictions] = useState<Selectable[]>([]);

    const [getJurisdictions] = useFindAllJurisdictionsLazyQuery();

    useEffect(() => {
        getJurisdictions().then((results) => {
            if (results?.data?.findAllJurisdictions) {
                const selectables = mapNonNull(asSelectable, results.data.findAllJurisdictions);
                setJurisdictions(selectables);
            }
        });
    }, []);

    return { all: jurisdictions, resolve: findByValue(jurisdictions) };
};

export { useJurisdictionOptions };
