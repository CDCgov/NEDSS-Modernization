import { useConfiguration } from 'configuration';
import { asSelectable, Selectable } from 'options';
import { useMemo } from 'react';
import { maybeMap } from 'utils/mapping';

const maybeAsSelectable = maybeMap(asSelectable);

type LocationDefaults = {
    state?: Selectable;
    country?: Selectable;
};

const useLocationDefaults = (): LocationDefaults => {
    const {
        settings: { defaults }
    } = useConfiguration();

    return useMemo(
        () => ({
            country: maybeAsSelectable(defaults.country)
        }),
        [defaults.country]
    );
};

export { useLocationDefaults };
export type { LocationDefaults };
