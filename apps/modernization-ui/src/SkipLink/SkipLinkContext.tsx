import React, { createContext, useContext, useState } from 'react';

interface SkipLinkContextType {
    setId: (id: string) => void;
}

// Create the context
export const SkipLinkContext = createContext<SkipLinkContextType | undefined>(undefined);

// Custom hook for the context
export function useSkipLink() {
    const context = useContext(SkipLinkContext);
    if (context === undefined) {
        throw new Error('useSkipLink must be used within a SkipLinkProvider');
    }
    return context;
}

interface SkipLinkProviderProps {
    children: React.ReactNode;
}

export const SkipLinkProvider: React.FC<SkipLinkProviderProps> = ({ children }: SkipLinkProviderProps) => {
    const [id, setId] = useState<string | undefined>(undefined);

    // Setting up the id to pass it to the anchor tag
    const contextValue: SkipLinkContextType = {
        setId
    };

    return (
        <SkipLinkContext.Provider value={contextValue}>
            <a href={'#' + id}>Skip to main content</a>
            {children}
        </SkipLinkContext.Provider>
    );
};
