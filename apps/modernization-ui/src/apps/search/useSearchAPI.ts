import { useEffect, useReducer } from 'react';
import { Page, Status as PageStatus, usePage } from 'page';
import { Sorting, useSorting } from 'sorting';
import { useSearch } from './useSearch';

type Results<R> = {
    total: number;
    page?: number;
    content: R[];
};

type Waiting = { status: 'waiting' };

type Requesting<C> = { status: 'requesting'; criteria: C };

type Searching<A> = { status: 'searching'; parameters: A };

type Completed<A, R> = { status: 'completed'; found: Results<R>; parameters: A };

type Failed = { status: 'error'; reason: string };

type State<C, A, R> = Waiting | Requesting<C> | Searching<A> | Completed<A, R> | Failed;

type Action<C, A, R> =
    | { type: 'reset' }
    | { type: 'refresh' }
    | { type: 'request'; criteria: C }
    | { type: 'search'; parameters: A }
    | { type: 'complete'; found: Results<R>; parameters: A }
    | { type: 'error'; reason: string };

const reducer = <C, A, R>(current: State<C, A, R>, action: Action<C, A, R>): State<C, A, R> => {
    if (action.type === 'request') {
        return { status: 'requesting', criteria: action.criteria };
    } else if (action.type === 'search') {
        return { status: 'searching', parameters: action.parameters };
    } else if (action.type === 'complete' && current.status === 'searching') {
        return { ...current, status: 'completed', found: action.found };
    } else if (action.type === 'refresh' && current.status === 'completed') {
        return { status: 'searching', parameters: current.parameters };
    } else if (action.type === 'error') {
        return { status: 'error', reason: action.reason };
    } else if (action.type === 'reset') {
        return { status: 'waiting' };
    }
    return current;
};

type ResultHandler<R> = (result: Results<R>) => void;

const orElseEmptyResult =
    <R>(handler: ResultHandler<R>) =>
    (result?: Results<R>) => {
        const ensured = result ?? { total: 0, content: [] };
        handler(ensured);
    };

type Interaction<C, R> = {
    status: 'waiting' | 'loading' | 'completed' | 'error';
    results?: Results<R>;
    error?: string;
    reset: () => void;
    search: (criteria: C) => void;
};

type Settings<C, A, R> = {
    transformer: (criteria: C) => A;
    resolver: (parameters: A, page: Page, sorting: Sorting) => Promise<Results<R> | undefined>;
};

const useSearchAPI = <C, A, R>({ transformer, resolver }: Settings<C, A, R>): Interaction<C, R> => {
    const { page, ready } = usePage();
    const { sorting } = useSorting();

    const searchResults = useSearch();

    const [state, dispatch] = useReducer(reducer<C, A, R>, { status: 'waiting' });

    const isLoading = state.status === 'searching' || state.status === 'requesting';

    useEffect(() => {
        if (isLoading) {
            searchResults.search();
        } else if (state.status === 'completed') {
            searchResults.complete([], state.found.total);
        } else if (state.status === 'waiting') {
            searchResults.reset();
        }
    }, [state.status, isLoading]);

    const handleComplete = (page: Page, parameters: A) => (result: Results<R>) => {
        const number = page.current + 1;
        dispatch({ type: 'complete', found: { ...result, page: number }, parameters });
        ready(result.total, number);
    };

    const handleError = (error: Error) => {
        dispatch({ type: 'error', reason: error.message });
    };

    useEffect(() => {
        if (state.status === 'requesting') {
            const parameters = transformer(state.criteria);
            dispatch({ type: 'search', parameters });
        } else if (state.status === 'searching') {
            resolver(state.parameters, page, sorting).then(
                orElseEmptyResult(handleComplete(page, state.parameters)),
                handleError
            );
        }
    }, [state.status]);

    useEffect(() => {
        if (page.status == PageStatus.Requested) {
            dispatch({ type: 'refresh' });
        }
    }, [page.status, dispatch]);

    const reset = () => dispatch({ type: 'reset' });
    const search = (criteria: C) => dispatch({ type: 'request', criteria });

    return {
        status: isLoading ? 'loading' : state.status,
        results: state.status === 'completed' ? state.found : undefined,
        error: state.status === 'error' ? state.reason : undefined,
        reset,
        search
    };
};

export type { Settings, Results, Interaction };
export { useSearchAPI };
