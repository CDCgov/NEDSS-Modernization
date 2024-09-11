import React, { createContext, useContext, useState } from 'react';
<<<<<<< HEAD
=======
import DataElementsContextProvider from './DataElementsContext'; // Import other context providers
import PatientMatchContextProvider from './PatientMatchContext';
>>>>>>> case-dedup-ui

const DedupeContext = createContext<DedupeContextProps | undefined>(undefined);

type DedupeContextProps = {
    mode: string;
    setMode: (mode: string) => void;
};

const DedupeContextProvider = ({ children }: { children: React.ReactNode }) => {
    const [mode, setMode] = useState('patient');
<<<<<<< HEAD
    return <DedupeContext.Provider value={{ mode, setMode }}>{children}</DedupeContext.Provider>;
=======

    return (
        <DedupeContext.Provider value={{ mode, setMode }}>
            <DataElementsContextProvider>
                <PatientMatchContextProvider>{children}</PatientMatchContextProvider>
            </DataElementsContextProvider>
        </DedupeContext.Provider>
    );
>>>>>>> case-dedup-ui
};

export const useDedupeContext = () => {
    const context = useContext(DedupeContext);
    if (context === undefined) {
        throw new Error('useDedupeContext must be used inside DedupeContextProvider');
    }
    return context;
};

export default DedupeContextProvider;
