import { createContext, ReactNode, useContext, useReducer } from 'react';
import { TOTAL_TABLE_DATA } from 'utils/util';

type Page = {
    pageSize: number;
    total: number;
    current: number;
};

type Action = { type: 'ready'; total: number; page: number } | { type: 'go-to'; page: number };
type Dispatch = (action: Action) => void;

const PageContext = createContext<{ page: Page; dispatch: Dispatch } | undefined>(undefined);

const pageReducer = (state: Page, action: Action) => {
    switch (action.type) {
        case 'ready':
            return { ...state, total: action.total, page: action.page };
        case 'go-to':
            return { ...state, current: action.page };
    }
};

type PageProviderProps = {
    pageSize?: number;
    children: ReactNode;
};

const PageProvider = ({ pageSize = TOTAL_TABLE_DATA, children }: PageProviderProps) => {
    const startPage = { pageSize, total: 0, current: 1 };

    const [page, dispatch] = useReducer(pageReducer, startPage);

    const value = { page, dispatch };

    return <PageContext.Provider value={value}>{children}</PageContext.Provider>;
};

const usePage = () => {
    const context = useContext(PageContext);

    if (context === undefined) {
        throw new Error('usePage must be used within a PageProvider');
    }

    return context;
};

const goToPage = (dispatch: Dispatch) => (page: number) => dispatch({ type: 'go-to', page: page });

export { PageProvider, usePage, goToPage };
