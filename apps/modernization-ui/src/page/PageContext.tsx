import React, { createContext, useCallback, useContext, useEffect, useState } from 'react';
import { useLocation } from 'react-router';
import { ScrollToTop } from './scrollToTop';

type UsePageInteraction = {
    title: string | undefined;
    setTitle: (title: string) => void;
    resetTitle: () => void;
};

const PageContext = createContext<UsePageInteraction | undefined>(undefined);

type PageProviderProps = {
    children: React.ReactNode;
};

export const PageProvider = ({ children }: PageProviderProps) => {
    const { pathname } = useLocation();

    const [title, applyTitle] = useState<string | undefined>();

    useEffect(() => {
        applyTitle((current) => (current ? current : resolveTitle(pathname)));
    }, [pathname, applyTitle]);

    const setTitle = useCallback(
        (value?: string) => {
            const title = value ? value : resolveTitle(pathname);
            applyTitle(title);
        },
        [applyTitle, pathname]
    );

    useEffect(() => {
        if (title) {
            const pageTitle = title ? `| ${title}` : '';
            document.title = `NBS ${pageTitle}`;
        }
    }, [title]);

    const resetTitle = useCallback(() => setTitle(undefined), [setTitle]);

    const value: UsePageInteraction = {
        title,
        setTitle,
        resetTitle
    };

    return (
        <PageContext.Provider value={value}>
            <ScrollToTop title={title || 'Main content'}>{children}</ScrollToTop>
        </PageContext.Provider>
    );
};

const resolveTitle = (value?: string) => {
    return value
        ?.split('/')[1]
        ?.split('-')
        .map((value) => value.charAt(0).toUpperCase() + value.slice(1))
        .join(' ');
};

export const usePage = () => {
    const context = useContext(PageContext);
    if (context === undefined) {
        throw new Error('usePage must be used within a PageProvider');
    }
    return context;
};
