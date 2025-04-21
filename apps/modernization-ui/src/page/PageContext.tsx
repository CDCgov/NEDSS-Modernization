import React, { createContext, useContext, useState } from 'react';

interface PageContextType {
    title: string | undefined;
    setTitle: (title: string) => void;
    resetTitle: () => void;
}

export const PageContext = createContext<PageContextType | undefined>(undefined);

export function usePage() {
    const context = useContext(PageContext);
    if (context === undefined) {
        throw new Error('usePage must be used within a PageProvider');
    }
    return context;
}

interface PageProviderProps {
    children: React.ReactNode;
}

export const PageProvider = ({ children }: PageProviderProps) => {
    const [title, setTitle] = useState<string | undefined>(undefined);

    const resetTitle = () => setTitle(undefined);

    const contextValue: PageContextType = {
        title,
        setTitle,
        resetTitle
    };

    return <PageContext.Provider value={contextValue}>{children}</PageContext.Provider>;
};
