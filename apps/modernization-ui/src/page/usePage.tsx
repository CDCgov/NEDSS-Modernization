import { createContext, ReactNode, useContext, useEffect, useReducer } from 'react';
import { useSearchParams } from 'react-router-dom';
import { TOTAL_TABLE_DATA } from 'utils/util';

const PAGE_PARAMETER = 'page';

enum Status {
    Ready,
    Requested
}

type PageState = {
    status: Status;
    pageSize: number;
    total: number;
    current: number;
};

type Action =
    | { type: 'ready'; total: number; page: number }
    | { type: 'go-to'; page: number }
    | { type: 'reload' }
    | { type: 'resize'; size: number };

const pageReducer = (state: PageState, action: Action): PageState => {
    switch (action.type) {
        case 'ready':
            return { ...state, status: Status.Ready, total: action.total, current: action.page };
        case 'go-to': {
            return state.current !== action.page ? { ...state, status: Status.Requested, current: action.page } : state;
        }
        case 'reload':
            return { ...state, status: Status.Requested };
        case 'resize': {
            return state.pageSize !== action.size
                ? { ...state, status: Status.Requested, pageSize: action.size, current: 1 }
                : state;
        }
    }
};

const initialize = (size: number): PageState => ({ status: Status.Ready, pageSize: size, total: 0, current: 0 });

type PageContextState = {
    page: PageState;
    firstPage: () => void;
    reload: () => void;
    request: (page: number) => void;
    ready: (total: number, page: number) => void;
    resize: (size: number) => void;
};

const PageContext = createContext<PageContextState | undefined>(undefined);

type PagingSettings = {
    appendToUrl?: boolean;
    pageSize?: number;
};

type PageProviderProps = {
    children: ReactNode;
} & PagingSettings;

const PageProvider = ({ pageSize = TOTAL_TABLE_DATA, appendToUrl = false, children }: PageProviderProps) => {
    const [page, dispatch] = useReducer(pageReducer, pageSize, initialize);

    const [searchParams, setSearchParams] = useSearchParams();

    const requested = searchParams.get(PAGE_PARAMETER);

    useEffect(() => {
        if (appendToUrl && requested) {
            //  when appending to the url the go-to dispatches are triggered by the page query param
            dispatch({ type: 'go-to', page: Number(requested) });
        }
    }, [appendToUrl, requested]);

    const requestFromUrl = (next: number) => {
        if (next != page.current) {
            // saves the current page to a url param so that it persists
            // on page refresh or navigating away
            setSearchParams((current) => {
                current.set(PAGE_PARAMETER, next.toString());
                return current;
            });
        }
    };

    const dispatchGoTo = (page: number) => {
        if (appendToUrl) {
            setSearchParams((current) => {
                current.set(PAGE_PARAMETER, page.toString());
                return current;
            });
        }
        dispatch({ type: 'go-to', page });
    };

    const requestFromDispatch = (page: number) => dispatchGoTo(page);

    const firstPage = () => dispatchGoTo(1);
    const reload = () => dispatch({ type: 'reload' });
    const request = appendToUrl ? requestFromUrl : requestFromDispatch;
    const ready = (total: number, page: number) => dispatch({ type: 'ready', total, page });
    const resize = (size: number) => dispatch({ type: 'resize', size });

    const value = { page, firstPage, reload, request, ready, resize };

    return <PageContext.Provider value={value}>{children}</PageContext.Provider>;
};

const usePage = () => {
    const context = useContext(PageContext);

    if (context === undefined) {
        throw new Error('usePage must be used within a PageProvider');
    }

    return context;
};

const usePageMaybe = () => {
    const context = useContext(PageContext);

    return context;
};

export { PageProvider, usePage, usePageMaybe };

export { Status };
export type { PageState as Page, PagingSettings };
