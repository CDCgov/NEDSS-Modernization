import { createContext, ReactNode, useContext, useMemo, useState } from 'react';
import { PagesResponse, PagesTab } from 'apps/page-builder/generated';

type Selected = PagesTab | undefined;

type StaticTab = 'contactRecord' | 'supplementalInfo' | undefined;

type Interactions = {
    page: PagesResponse;
    selected?: Selected;
    fetch: (page: number) => void;
    refresh: () => void;
    select: (tab: PagesTab) => void;
    loading: boolean;
    displayStaticTab: StaticTab;
    selectStaticTab: (tab: StaticTab) => void;
};

export const PageManagementContext = createContext<Interactions | undefined>(undefined);

type PageManagementProviderProps = {
    page: PagesResponse;
    fetch: (page: number) => void;
    refresh: () => void;
    children: ReactNode;
    loading: boolean;
};

const PageManagementProvider = ({ page, children, fetch, refresh, loading }: PageManagementProviderProps) => {
    const [selected, setSelected] = useState<number>(0);
    const [displayStaticTab, setDisplayStaticTab] = useState<StaticTab>(undefined);

    const select = (tab: PagesTab) => {
        setDisplayStaticTab(undefined);
        setSelected(page.tabs?.indexOf(tab) ?? 0);
    };

    const selectStaticTab = (tab: StaticTab) => {
        setDisplayStaticTab(tab);
        setSelected(-1);
    };

    const base = useMemo(() => {
        return {
            page,
            selected: page.tabs?.[selected],
            fetch,
            refresh,
            select,
            displayStaticTab,
            selectStaticTab
        };
    }, [JSON.stringify(page), selected, displayStaticTab]);

    const value = useMemo(() => {
        return { ...base, loading: loading };
    }, [base, loading]);

    return <PageManagementContext.Provider value={value}>{children}</PageManagementContext.Provider>;
};

const usePageManagement = () => {
    const context = useContext(PageManagementContext);

    if (context === undefined) {
        throw new Error('usePageManagement must be used within a PageManagementProvider');
    }

    return context;
};

export { PageManagementProvider, usePageManagement };
