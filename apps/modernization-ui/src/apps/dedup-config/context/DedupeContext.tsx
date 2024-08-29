import React, { createContext, useContext, useState } from 'react';

const DedupeContext = createContext<DedupeContextProps | undefined>(undefined);

type DedupeContextProps = {
    mode: string;
    setMode: (mode: string) => void;
};

const DedupeContextProvider = ({ children }: { children: React.ReactNode }) => {
    const [mode, setMode] = useState('patient');
    return <DedupeContext.Provider value={{ mode, setMode }}>{children}</DedupeContext.Provider>;
};

export const useDedupeContext = () => {
    const context = useContext(DedupeContext);
    if (context === undefined) {
        throw new Error('useDedupeContext must be used inside DedupeContextProvider');
    }
    return context;
};

export default DedupeContextProvider;
