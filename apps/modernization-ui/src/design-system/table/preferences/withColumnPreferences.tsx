import { FC, ComponentType } from 'react';

import { ColumnPreference } from './preference';
import { ColumnPreferenceProvider } from './useColumnPreferences';

type WithColumnPreferencesOptions = {
    /** The local storage key to use to read/write column preferences.
     *  Example: 'search.patients.preferences.columns'
     */
    storageKey: string;
    defaults?: ColumnPreference[];
};

export function withColumnPreferences<T extends object>(
    WrappedComponent: ComponentType<T>,
    options: WithColumnPreferencesOptions
): FC<T> {
    const { storageKey, defaults } = options;

    const EnhancedComponent: FC<T> = (props) => {
        return (
            <ColumnPreferenceProvider id={storageKey} defaults={defaults}>
                <WrappedComponent {...props} />
            </ColumnPreferenceProvider>
        );
    };

    return EnhancedComponent;
}
