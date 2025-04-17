import React, { createContext, useContext, useState } from 'react';

interface PageTitleContextType {
    pageTitle: string;
    setPageTitle: (pageTitle: string) => void;
}

export const PageTitleContext = createContext<PageTitleContextType | undefined>(undefined);

export function usePageTitle() {
    const context = useContext(PageTitleContext);
    if (context === undefined) {
        throw new Error('usePageTitle must be used within a PageTitleProvider');
    }
    return context;
}

interface PageTitleProviderProps {
    children: React.ReactNode;
}

export const PageTitleProvider = ({ children }: PageTitleProviderProps) => {
    const [pageTitle, setPageTitle] = useState('');

    const contextValue: PageTitleContextType = {
        pageTitle,
        setPageTitle
    };

    return <PageTitleContext.Provider value={contextValue}>{children}</PageTitleContext.Provider>;
};
