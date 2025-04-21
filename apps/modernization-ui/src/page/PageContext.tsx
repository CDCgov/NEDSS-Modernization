import React, { createContext, useCallback, useContext, useState } from 'react';
import { useLocation } from 'react-router';

interface UsePageInteraction {
    title: string | undefined;
    setTitle: (title: string) => void;
    resetTitle: () => void;
}

export const PageContext = createContext<UsePageInteraction | undefined>(undefined);

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
    const { pathname } = useLocation();

    const [title, _setTitle] = useState<string | undefined>(() => resolveTitle(pathname));

    const setTitle = useCallback(
        (value?: string) => {
            if (value) {
                _setTitle(value);
            } else {
                _setTitle(resolveTitle(pathname));
            }
        },
        [_setTitle, pathname]
    );

    const resetTitle = useCallback(() => setTitle(undefined), [setTitle]);

    const value: UsePageInteraction = {
        title,
        setTitle,
        resetTitle
    };

    return <PageContext.Provider value={value}>{children}</PageContext.Provider>;
};

const resolveTitle = (value?: string) =>
    value
        ?.split('/')[1]
        ?.split('-')
        .map((value) => value.charAt(0).toUpperCase() + value.slice(1))
        .join(' ');
