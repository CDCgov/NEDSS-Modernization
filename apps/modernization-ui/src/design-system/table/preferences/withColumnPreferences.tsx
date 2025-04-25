import React from 'react';
import { ColumnPreference, ColumnPreferenceProvider } from './ColumnPreferenceProvider';

type WithColumnPreferencesOptions = {
    /** The local storage key to use to read/write column preferences. If not provided, then component not wrapped
     * in a provider. Example: 'search.patients.preferences.columns'
     */
    storageKey?: string;
    defaults?: ColumnPreference[];
};

export function withColumnPreferences<T extends object>(
    WrappedComponent: React.ComponentType<T>,
    options: WithColumnPreferencesOptions
): React.FC<T> {
    const { storageKey, defaults } = options;

    const EnhancedComponent: React.FC<T> = (props) => {
        if (storageKey) {
            return (
                <ColumnPreferenceProvider id={storageKey} defaults={defaults}>
                    <WrappedComponent {...props} />
                </ColumnPreferenceProvider>
            );
        }

        return <WrappedComponent {...props} />;
    };

    return EnhancedComponent;
}
