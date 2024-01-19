import React, { createContext, useContext, useEffect, useState } from 'react';
import './SkipLink.scss';
import { focusedTarget } from 'utils';

interface SkipLinkContextType {
    skipTo: (id: string) => void;
    previousTarget: (id: string) => void;
    currentFocusTarget: string;
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
    const [currentFocusTarget, setCurrentFocusTarget] = useState('');
    const [previousFocusTarget, setPreviousFocusTarget] = useState('');

    const skipTo = (id: string) => {
        setCurrentFocusTarget(id);
    };

    const previousTarget = (id: string) => {
        setPreviousFocusTarget(id);
    };

    useEffect(() => {
        const handleKeyDown = (event: KeyboardEvent) => {
            if (event.altKey && event.keyCode === 88) {
                event.preventDefault();
                focusedTarget(previousFocusTarget);
            }
        };
        window.addEventListener('keydown', handleKeyDown);
    }, [previousFocusTarget]);

    // Setting up the id to pass it to the anchor tag
    const contextValue: SkipLinkContextType = {
        skipTo,
        previousTarget,
        currentFocusTarget
    };

    return (
        <SkipLinkContext.Provider value={contextValue}>
            {currentFocusTarget && (
                <a href={`#${currentFocusTarget}`} className="skip-link">
                    Skip to main content
                </a>
            )}
            {children}
        </SkipLinkContext.Provider>
    );
};
