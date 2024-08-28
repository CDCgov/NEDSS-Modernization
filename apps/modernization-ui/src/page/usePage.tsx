import { createContext, ReactNode, useCallback, useContext, useEffect, useReducer } from 'react';
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
    | { type: 'reset' }
    | { type: 'resize'; size: number };

const pageReducer = (state: PageState, action: Action): PageState => {
    switch (action.type) {
        case 'ready':
            return { ...state, status: Status.Ready, total: action.total, current: action.page };
        case 'go-to': {
            return { ...state, status: Status.Requested, current: action.page };
        }
        case 'reload':
            return { ...state, status: Status.Requested };
        case 'resize': {
            return state.pageSize !== action.size
                ? { ...state, status: Status.Requested, pageSize: action.size, current: 1 }
                : state;
        }
        case 'reset': {
            return initialize(state.pageSize, 1);
        }
    }
};

const initialize = (size: number, current = 0): PageState => ({
    status: Status.Ready,
    pageSize: size,
    total: 0,
    current
});

type PageContextState = {
    page: PageState;
    firstPage: () => void;
    reload: () => void;
    request: (page: number) => void;
    ready: (total: number, page: number) => void;
    resize: (size: number) => void;
    reset: () => void;
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

    const requestFromUrl = useCallback(
        (next: number) => {
            if (next != page.current) {
                // saves the current page to a url param so that it persists
                // on page refresh or navigating away
                setSearchParams((current) => {
                    current.set(PAGE_PARAMETER, next.toString());
                    return current;
                });
            }
        },
        [page.current, setSearchParams]
    );

    const requestFromDispatch = useCallback((page: number) => dispatch({ type: 'go-to', page }), [dispatch]);
    const reload = useCallback(() => dispatch({ type: 'reload' }), [dispatch]);
    const request = appendToUrl ? requestFromUrl : requestFromDispatch;
    const firstPage = () => request(1);
    const ready = useCallback((total: number, page: number) => dispatch({ type: 'ready', total, page }), [dispatch]);
    const resize = useCallback((size: number) => dispatch({ type: 'resize', size }), [dispatch]);
    const reset = useCallback(() => dispatch({ type: 'reset' }), [dispatch]);

    const value = { page, firstPage, reload, request, ready, resize, reset };

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
