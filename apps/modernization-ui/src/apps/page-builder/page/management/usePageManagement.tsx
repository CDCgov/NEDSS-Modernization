import { createContext, ReactNode, useContext, useState } from 'react';
import { PagesResponse, PagesTab } from 'apps/page-builder/generated';

type Selected = PagesTab | undefined;

type Interactions = {
    page: PagesResponse;
    selected?: Selected;
    select: (tab: PagesTab) => void;
};

const PageManagementContext = createContext<Interactions | undefined>(undefined);

const resolveInitial = (tabs?: PagesTab[]) => (tabs && tabs.length > 0 ? tabs[0] : undefined);

type PageManagementProviderProps = {
    page: PagesResponse;
    children: ReactNode;
};

const PageManagementProvider = ({ page, children }: PageManagementProviderProps) => {
    const [selected, setSelected] = useState<Selected>(() => resolveInitial(page.tabs));

    const select = (tab: PagesTab) => setSelected(tab);

    const value = {
        page,
        selected,
        select
    };

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
