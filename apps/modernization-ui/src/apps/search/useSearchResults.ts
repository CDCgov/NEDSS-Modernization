import { useCallback, useEffect, useMemo, useReducer, useState } from 'react';
import { usePagination, Status as PageStatus } from 'pagination';
import { useSorting } from 'libs/sorting';
import { Predicate } from 'utils';
import { Filter, maybeUseFilter } from 'design-system/filter';
import { useSearchCriteria } from './useSearchCriteria';
import { SearchInteractionStatus, SearchResults } from './useSearchInteraction';
import { Term } from './terms';

type Page = { number: number; size: number };
type Filtering = { overallTotal: number; filtering: boolean };

type Resolved<R> = Omit<SearchResults<R>, 'terms'>;

type Waiting = { status: 'waiting' };

type Resetting = { status: 'resetting' };

type Initializing<C> = { status: 'initializing'; criteria: C; page: Page };

type Requesting<C> = { status: 'requesting'; criteria: C; page: Page };

type Fetching<A> = { status: 'fetching'; parameters: A; terms: Term[]; page: Page };
type Refetching<A, R> = {
    status: 'reloading';
    parameters: A;
    terms: Term[];
    page: Page;
    results: SearchResults<R>;
};

type Completed<A, R> = {
    status: 'completed';
    parameters: A;
    results: SearchResults<R>;
    filter: Filtering;
};

type Failed = { status: 'error'; reason: string };

type NoInput<R> = { status: 'no-input'; results: SearchResults<R> };

type State<C, A, R> =
    | Waiting
    | Initializing<C>
    | Resetting
    | Requesting<C>
    | Fetching<A>
    | Refetching<A, R>
    | Completed<A, R>
    | Failed
    | NoInput<R>;

type Action<C, A, R> =
    | { type: 'wait' }
    | { type: 'reset' }
    | { type: 'change-sort' }
    | { type: 'change-filter' }
    | ({ type: 'change-page' } & Page)
    | { type: 'initialize'; criteria: C; page: Page }
    | { type: 'request'; criteria: C; page: Page }
    | { type: 'fetch'; parameters: A; terms: Term[]; page: Page }
    | { type: 'complete'; found: Resolved<R>; filter: Filtering }
    | { type: 'no-input'; parameters: A; page: Page }
    | { type: 'error'; reason: string };

const reducer = <C, A, R>(current: State<C, A, R>, action: Action<C, A, R>): State<C, A, R> => {
    switch (action.type) {
        case 'request': {
            const { criteria, page } = action;
            return { status: 'requesting', criteria, page };
        }
        case 'fetch': {
            const { parameters, terms, page } = action;
            return { status: 'fetching', parameters, terms, page };
        }
        case 'error': {
            return { status: 'error', reason: action.reason };
        }
        case 'reset': {
            return { status: 'resetting' };
        }
        case 'wait': {
            return { status: 'waiting' };
        }
        case 'no-input': {
            return {
                status: 'no-input',
                results: { total: 0, content: [], terms: [], page: action.page.number, size: action.page.size }
            };
        }
        case 'initialize': {
            const { page } = action;
            return { status: 'initializing', criteria: action.criteria, page };
        }
        case 'complete': {
            if (current.status === 'fetching' || current.status === 'reloading') {
                const { found, filter } = action;
                return {
                    ...current,
                    status: 'completed',
                    results: {
                        ...action.found,
                        total: filter.filtering ? filter.overallTotal : found.total,
                        filteredTotal: filter.filtering ? found.total : undefined,
                        terms: current.terms
                    },
                    filter: filter
                };
            }
            break;
        }
        case 'change-page': {
            if (current.status === 'completed') {
                return {
                    status: 'reloading',
                    parameters: current.parameters,
                    terms: current.results.terms,
                    page: { number: action.number, size: action.size },
                    results: current.results
                };
            }
            break;
        }
        case 'change-filter':
        case 'change-sort': {
            if (current.status === 'completed') {
                const page = { number: 1, size: current.results.size };

                return {
                    status: 'reloading',
                    parameters: current.parameters,
                    terms: current.results.terms,
                    page,
                    results: current.results
                };
            }
            break;
        }
    }

    return current;
};

const orElseEmptyResult =
    <A, R>(request: ResultRequest<A>) =>
    (result?: Resolved<R>) =>
        result ?? { size: request.page.size, total: 0, content: [], page: 0 };

const adjustResult = <R>(result: Resolved<R>) => {
    const { page, ...remaining } = result;

    return { ...remaining, page: page + 1 };
};

const defaultNoInputCheck: Predicate<Term[]> = (terms: Term[]) => terms.length === 0;

const blankResults = (size: number, page: number) => ({
    total: 0,
    content: [],
    terms: [],
    page,
    size
});

type SearchResultsInteraction<C, R> = {
    status: SearchInteractionStatus;
    criteria?: C;
    results: SearchResults<R>;
    error?: string;
    reset: () => void;
    search: (criteria: C) => void;
};

type Transformer<C, A> = (criteria: C) => A;

type SortRequest = {
    property: string;
    direction: string;
};

type ResultRequest<A> = {
    parameters: A;
    page: { number: number; size: number };
    sort?: SortRequest;
    filter?: Filter;
};
type ResultResolver<A, R> = (request: ResultRequest<A>) => Promise<Resolved<R> | undefined>;
type TermResolver<C> = (criteria: C) => Term[];

type SearchResultSettings<C, A, R> = {
    transformer: Transformer<C, A>;
    resultResolver: ResultResolver<A, R>;
    termResolver: TermResolver<C>;
    defaultValues?: C | ((obj: C) => C);
    noInputCheck?: Predicate<Term[]>;
};

const useSearchResults = <C extends object, A extends object, R extends object>({
    transformer,
    resultResolver,
    termResolver,
    noInputCheck = defaultNoInputCheck,
    defaultValues
}: SearchResultSettings<C, A, R>): SearchResultsInteraction<C, R> => {
    const { page, ready, reset: pageReset } = usePagination();
    const { property, direction } = useSorting();
    const filtering = maybeUseFilter();
    const [currentTotal, setCurrentTotal] = useState<number>(0);

    const sort = useMemo(() => {
        if (property && direction) {
            return {
                property,
                direction
            };
        }
    }, [property, direction]);

    const filter: Filtering = useMemo(
        () => ({
            overallTotal: currentTotal,
            filtering: !!filtering?.filter
        }),
        [currentTotal, filtering]
    );

    const {
        criteria: searchCriteria,
        clear: clearCriteria,
        change: changeCriteria
    } = useSearchCriteria({ defaultValues });

    const [state, dispatch] = useReducer(reducer<C, A, R>, { status: 'waiting' });

    useEffect(() => {
        if (state.status === 'resetting') {
            dispatch({ type: 'wait' });
        }
    }, [state.status, dispatch]);

    useEffect(() => {
        if (state.status === 'waiting' || state.status === 'no-input') {
            //  reset the page when waiting for
            pageReset();
        }
    }, [state.status, pageReset]);

    useEffect(() => {
        if (searchCriteria) {
            //  the search criteria has changed initialize a search
            dispatch({ type: 'initialize', criteria: searchCriteria, page: { number: 1, size: page.pageSize } });

            if (filtering) {
                filtering.reset();
            }
        }
    }, [searchCriteria, dispatch]);

    useEffect(() => {
        if (state.status === 'completed' && !searchCriteria) {
            //  the search criteria has removed, reset the search
            dispatch({ type: 'reset' });
        }
    }, [state.status, searchCriteria, dispatch]);

    useEffect(() => {
        if (state.status === 'resetting') {
            clearCriteria();

            if (filtering) {
                filtering.reset();
            }
        }
    }, [state.status, clearCriteria]);

    useEffect(() => {
        if (state.status === 'requesting') {
            const { criteria, page } = state;

            const parameters = transformer(criteria);
            const terms = termResolver(criteria);

            if (noInputCheck(terms)) {
                dispatch({ type: 'no-input', parameters, page });
            } else {
                changeCriteria(state.criteria);
            }
        }
    }, [state.status, changeCriteria]);

    useEffect(() => {
        if (state.status === 'initializing') {
            const parameters = transformer(state.criteria);
            const terms = termResolver(state.criteria);

            if (noInputCheck(terms)) {
                dispatch({ type: 'no-input', parameters, page: state.page });
            } else {
                dispatch({ type: 'fetch', parameters, terms, page: state.page });
            }
        }
    }, [state.status, noInputCheck]);

    useEffect(() => {
        if (state.status === 'fetching' || state.status === 'reloading') {
            // the criteria has changed invoke search
            const request = {
                parameters: state.parameters,
                page: state.page,
                sort,
                filter: filtering?.filter
            };

            resultResolver(request)
                .then(orElseEmptyResult(request))
                .then(adjustResult)
                .then(handleComplete, handleError);
        }
    }, [state.status]);

    useEffect(() => {
        if (state.status === 'completed' && page.status === PageStatus?.Requested) {
            //  the page changing without the criteria changing
            dispatch({ type: 'change-page', number: page.current, size: page.pageSize });
        }
    }, [state.status, page.status, page.pageSize, page.current]);

    useEffect(() => {
        if (sort?.direction) {
            //  the sorting changing without the criteria changing
            dispatch({ type: 'change-sort' });
        }
    }, [sort?.direction, sort?.property]);

    useEffect(() => {
        dispatch({ type: 'change-filter' });
    }, [filtering?.filter]);

    const handleComplete = (resolved: Resolved<R>) => {
        ready(resolved.total, resolved.page);
        // set the current total since we are not filtering
        // when a filter is later applied we can use the value to determine the overall total
        if (!filter.filtering) {
            setCurrentTotal(resolved.total);
        }
        dispatch({ type: 'complete', found: { ...resolved }, filter: filter });
    };

    const handleError = (error: Error) => dispatch({ type: 'error', reason: error.message });

    const status = useMemo(
        () => (state.status === 'fetching' || state.status === 'requesting' ? 'loading' : state.status),
        [state.status]
    );
    const criteria = useMemo(
        () => (state.status === 'initializing' || state.status === 'requesting' ? state.criteria : undefined),
        [state.status]
    );
    const results = useMemo(
        () =>
            state.status === 'completed' || state.status === 'reloading'
                ? state.results
                : blankResults(page.pageSize, page.current),
        [state.status, page.pageSize, page.current]
    );
    const error = useMemo(() => (state.status === 'error' ? state.reason : undefined), [state.status]);
    const reset = useCallback(() => dispatch({ type: 'reset' }), [dispatch]);
    const search = useCallback(
        (criteria: C) => {
            dispatch({ type: 'request', criteria, page: { number: 1, size: page.pageSize } });
        },
        [dispatch, page.pageSize]
    );

    return {
        status,
        criteria,
        results,
        error,
        reset,
        search
    };
};

export type { SearchResultSettings, ResultRequest, Resolved, SearchResultsInteraction };
export { useSearchResults };
