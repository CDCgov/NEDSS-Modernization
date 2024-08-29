import React from 'react';
import DataElementsContextProvider from './DataElementsContext';
import DedupeContextProvider from './DedupeContext';

const PatientMatchContextProvider = ({ children }: { children: React.ReactNode }) => {
    return (
        // Add other ContextProviders here
        <DedupeContextProvider>
            <DataElementsContextProvider>{children}</DataElementsContextProvider>
        </DedupeContextProvider>
    );
};

export default PatientMatchContextProvider;
