import React, { createContext, useContext, useState } from 'react';
import './SkipLink.scss';

interface SkipLinkContextType {
    skipTo: (id: string) => void;
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

export const SkipLinkProvider = ({ children }: SkipLinkProviderProps) => {
    const [id, skipTo] = useState<string | undefined>(undefined);

    // Setting up the id to pass it to the anchor tag
    const contextValue: SkipLinkContextType = {
        skipTo
    };
    return (
        <SkipLinkContext.Provider value={contextValue}>
            {id && (
                <a href={`#${id}`} className="skip-link">
                    Skip to main content
                </a>
            )}
            {children}
        </SkipLinkContext.Provider>
    );
};
