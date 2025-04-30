import React from 'react';
import { ColumnPreference, ColumnPreferenceProvider } from './useColumnPreferences';

type WithColumnPreferencesOptions = {
    /** The local storage key to use to read/write column preferences.
     *  Example: 'search.patients.preferences.columns'
     */
    storageKey: string;
    defaults?: ColumnPreference[];
};

export function withColumnPreferences<T extends object>(
    WrappedComponent: React.ComponentType<T>,
    options: WithColumnPreferencesOptions
): React.FC<T> {
    const { storageKey, defaults } = options;

    const EnhancedComponent: React.FC<T> = (props) => {
        return (
            <ColumnPreferenceProvider id={storageKey} defaults={defaults}>
                <WrappedComponent {...props} />
            </ColumnPreferenceProvider>
        );
    };

    return EnhancedComponent;
}
