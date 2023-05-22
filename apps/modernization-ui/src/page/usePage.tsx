import { createContext, ReactNode, useContext, useReducer } from 'react';
import { TOTAL_TABLE_DATA } from 'utils/util';

enum Status {
    Requested,
    Ready
}

type Page = {
    status: Status;
    pageSize: number;
    total: number;
    current: number;
};

type Action = { type: 'ready'; total: number; page: number } | { type: 'go-to'; page: number } | { type: 'reload' };

const pageReducer = (state: Page, action: Action) => {
    switch (action.type) {
        case 'ready':
            return { ...state, status: Status.Ready, total: action.total, current: action.page };
        case 'go-to':
            return { ...state, status: Status.Requested, current: action.page };
        case 'reload':
            return { ...state, status: Status.Requested };
    }
};

type PageContextState = {
    page: Page;
    firstPage: () => void;
    reload: () => void;
    request: (page: number) => void;
    ready: (total: number, page: number) => void;
};

const PageContext = createContext<PageContextState | undefined>(undefined);

type PageProviderProps = {
    pageSize?: number;
    children: ReactNode;
};

const PageProvider = ({ pageSize = TOTAL_TABLE_DATA, children }: PageProviderProps) => {
    const startPage = { status: Status.Ready, pageSize, total: 0, current: 1 };

    const [page, dispatch] = useReducer(pageReducer, startPage);

    const firstPage = () => dispatch({ type: 'go-to', page: 1 });
    const reload = () => dispatch({ type: 'reload' });
    const request = (page: number) => dispatch({ type: 'go-to', page: page });
    const ready = (total: number, page: number) => dispatch({ type: 'ready', total, page });

    const value = { page, firstPage, reload, request, ready };

    return <PageContext.Provider value={value}>{children}</PageContext.Provider>;
};

const usePage = () => {
    const context = useContext(PageContext);

    if (context === undefined) {
        throw new Error('usePage must be used within a PageProvider');
    }

    return context;
};

export { PageProvider, usePage };

export { Status };
export type { Page };
