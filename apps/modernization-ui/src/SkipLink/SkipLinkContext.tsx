import React, { createContext, useContext, useState } from 'react';

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
    const [showSkipLink, setShowSkipLink] = useState(false);
    const [skipLink, setSkipLink] = useState(true);

    // Setting up the id to pass it to the anchor tag
    const contextValue: SkipLinkContextType = {
        skipTo
    };

    const handleKeyDown = (e: { key: string }) => {
        if (e.key === 'Tab' && !showSkipLink && skipLink) {
            setShowSkipLink(true);
            setSkipLink(true);
        }
    };

    return (
        <SkipLinkContext.Provider value={contextValue}>
            <div onKeyDown={handleKeyDown} tabIndex={0}>
                {id && showSkipLink && skipLink && (
                    <a href={`#${id}`} onClick={() => setSkipLink(false)}>
                        Skip to main content
                    </a>
                )}
                {children}
            </div>
        </SkipLinkContext.Provider>
    );
};
