import React, { createContext, useContext, useState } from 'react';

interface SkipLinkContextType {
    skipTo: (id: string) => void;
    remove: (id: string) => void;
    currentFocusTargets: string[];
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
    const [currentFocusTargets, setCurrentFocusTargets] = useState<string[]>([]);

    const skipTo = (id: string) => {
        setCurrentFocusTargets((prev) => [...prev, id]);
    };

    const remove = (id: string) => {
        setCurrentFocusTargets((prev) => prev.filter((target) => target !== id));
    };

    const contextValue: SkipLinkContextType = {
        skipTo,
        remove,
        currentFocusTargets
    };

    const currentTarget =
        currentFocusTargets.length > 0 ? currentFocusTargets[currentFocusTargets.length - 1] : undefined;

    return (
        <SkipLinkContext.Provider value={contextValue}>
            {currentTarget && (
                <a href={`#${currentTarget}`} className="usa-skipnav">
                    Skip to main content
                </a>
            )}
            {children}
        </SkipLinkContext.Provider>
    );
};
