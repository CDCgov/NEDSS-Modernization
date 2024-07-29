import { useEffect, useMemo, useReducer } from 'react';
import { usePage, Status as PageStatus } from 'page';
import { Direction, useSorting } from 'sorting';
import { Term, useSearchResultDisplay } from './useSearchResultDisplay';
import { SortDirection, SortField } from 'generated/graphql/schema';

type Resolved<R> = {
    total: number;
    page: number;
    content: R[];
};

type Results<R> = Resolved<R> & {
    terms: Term[];
};

type Waiting = { status: 'waiting' };

type Requesting<C> = { status: 'requesting'; criteria: C };

type Fetching<A> = { status: 'fetching'; parameters: A; terms: Term[] };

type Completed<A, R> = { status: 'completed'; parameters: A; results: Results<R> };

type Failed = { status: 'error'; reason: string };

type NoInput<R> = { status: 'noInput'; results: Results<R> };

type State<C, A, R> = Waiting | Requesting<C> | Fetching<A> | Completed<A, R> | Failed | NoInput<R>;

type Action<C, A, R> =
    | { type: 'reset' }
    | { type: 'refresh' }
    | { type: 'request'; criteria: C }
    | { type: 'fetch'; parameters: A; terms: Term[] }
    | { type: 'complete'; found: Resolved<R> }
    | { type: 'noInput'; parameters: A; page: number }
    | { type: 'error'; reason: string };

const reducer = <C, A, R>(current: State<C, A, R>, action: Action<C, A, R>): State<C, A, R> => {
    if (action.type === 'request') {
        return { status: 'requesting', criteria: action.criteria };
    } else if (action.type === 'fetch') {
        return { status: 'fetching', parameters: action.parameters, terms: action.terms };
    } else if (action.type === 'complete' && current.status === 'fetching') {
        return { ...current, status: 'completed', results: { ...action.found, terms: current.terms } };
    } else if (action.type === 'refresh' && current.status === 'completed') {
        return { status: 'fetching', parameters: current.parameters, terms: current.results.terms };
    } else if (action.type === 'error') {
        return { status: 'error', reason: action.reason };
    } else if (action.type === 'reset') {
        return { status: 'waiting' };
    } else if (action.type === 'complete' && current.status === 'requesting') {
        return {
            status: 'noInput',
            results: { total: 0, content: [], terms: [], page: action.found.page }
        };
    }
    return current;
};

type ResultHandler<R> = (result: Resolved<R>) => void;

const orElseEmptyResult =
    <R>(handler: ResultHandler<R>) =>
    (result?: Resolved<R>) => {
        const ensured = result ?? { total: 0, content: [], page: 0 };
        handler(ensured);
    };

type Interaction<C, R> = {
    status: 'waiting' | 'loading' | 'completed' | 'error' | 'noInput';
    results?: Results<R>;
    error?: string;
    reset: () => void;
    search: (criteria: C) => void;
};

type Tranformer<C, A> = (criteria: C) => A;

type ResultRequest<A> = {
    parameters: A;
    page: { number: number; size: number };
    sort?: {
        property: SortField;
        direction: SortDirection;
    };
};
type ResultResolver<A, R> = (request: ResultRequest<A>) => Promise<Resolved<R> | undefined>;
type TermResolver<C> = (criteria: C) => Term[];

type Settings<C, A, R> = {
    transformer: Tranformer<C, A>;
    resultResolver: ResultResolver<A, R>;
    termResolver: TermResolver<C>;
};

const useSearch = <C, A, R>({ transformer, resultResolver, termResolver }: Settings<C, A, R>): Interaction<C, R> => {
    const { page, ready, reset: pageReset } = usePage();

    const { property, direction } = useSorting();

    const sort = useMemo(() => {
        if (property && direction) {
            return {
                property: asSortField(property),
                direction: asSortDirection(direction)
            };
        }
    }, [property, direction]);

    const searchResults = useSearchResultDisplay();

    const [state, dispatch] = useReducer(reducer<C, A, R>, { status: 'waiting' });

    const isLoading = state.status === 'fetching' || state.status === 'requesting';
    const results = state.status === 'completed' ? state.results : undefined;
    const error = state.status === 'error' ? state.reason : undefined;

    useEffect(() => {
        if (isLoading) {
            searchResults.search();
        } else if (state.status === 'completed') {
            searchResults.complete(state.results.terms);
        } else if (state.status === 'waiting') {
            pageReset();
            searchResults.reset();
        } else if (state.status === 'noInput') {
            pageReset();
            searchResults.noInput();
        }
    }, [state.status, isLoading]);

    const handleComplete = (page: number) => (resolved: Resolved<R>) => {
        ready(resolved.total, page);
        dispatch({ type: 'complete', found: { ...resolved, page } });
    };

    const handleError = (error: Error) => dispatch({ type: 'error', reason: error.message });

    useEffect(() => {
        if (state.status === 'requesting') {
            const parameters = transformer(state.criteria);
            const terms = termResolver(state.criteria);
            terms.length === 0
                ? dispatch({ type: 'complete', found: { total: 0, page: page.current, content: [] } })
                : dispatch({ type: 'fetch', parameters, terms });
        }
    }, [state.status]);

    useEffect(() => {
        if (state.status === 'fetching') {
            // the criteria has changed invoke search
            resultResolver({
                parameters: state.parameters,
                page: {
                    number: page.current,
                    size: page.pageSize
                },
                sort
            }).then(orElseEmptyResult(handleComplete(page.current)), handleError);
        } else if (state.status === 'completed' && page.status === PageStatus.Requested) {
            //  the page changing without the criteria changing
            dispatch({ type: 'refresh' });
        }
    }, [state.status, page.status, page.pageSize, page.current]);

    useEffect(() => {
        if (sort?.direction) {
            //  the sorting changing without the criteria changing
            dispatch({ type: 'refresh' });
        }
    }, [sort?.direction, sort?.property]);

    const reset = () => dispatch({ type: 'reset' });
    const search = (criteria: C) => dispatch({ type: 'request', criteria });

    return {
        status: isLoading ? 'loading' : state.status,
        results,
        error,
        reset,
        search
    };
};

const asSortDirection = (direction: Direction): SortDirection => {
    switch (direction) {
        case Direction.Ascending: {
            return SortDirection.Asc;
        }
        default: {
            return SortDirection.Desc;
        }
    }
};

const asSortField = (property: string): SortField => {
    switch (property) {
        case SortField.BirthTime: {
            return SortField.BirthTime;
        }
        case SortField.LastNm: {
            return SortField.LastNm;
        }
        case SortField.Sex: {
            return SortField.Sex;
        }
        case SortField.Address: {
            return SortField.Address;
        }
        case SortField.Email: {
            return SortField.Email;
        }
        case SortField.PhoneNumber: {
            return SortField.PhoneNumber;
        }
        case SortField.Id: {
            return SortField.LocalId;
        }
        case SortField.Identification: {
            return SortField.Identification;
        }
        default:
            return SortField.Relevance;
    }
};

export type { Settings, ResultRequest, Resolved, Interaction };
export { useSearch };
