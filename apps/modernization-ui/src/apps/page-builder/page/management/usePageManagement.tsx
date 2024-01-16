import { createContext, ReactNode, useContext, useMemo, useState } from 'react';
import { PagesResponse, PagesTab } from 'apps/page-builder/generated';

type Selected = PagesTab | undefined;

type Interactions = {
    page: PagesResponse;
    selected?: Selected;
    fetch: (page: number) => void;
    select: (tab: PagesTab) => void;
};

export const PageManagementContext = createContext<Interactions | undefined>(undefined);

type PageManagementProviderProps = {
    page: PagesResponse;
    fetch: (page: number) => void;
    children: ReactNode;
};

const PageManagementProvider = ({ page, children, fetch }: PageManagementProviderProps) => {
    const [selected, setSelected] = useState<number>(0);

    const select = (tab: PagesTab) => setSelected(page.tabs?.indexOf(tab) ?? 0);
    const value = useMemo(() => {
        return {
            page,
            selected: page.tabs?.[selected],
            fetch,
            select
        };
    }, [JSON.stringify(page)]);

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
