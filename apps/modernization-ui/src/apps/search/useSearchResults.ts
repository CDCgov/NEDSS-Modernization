import { useCallback, useEffect, useMemo, useReducer } from 'react';
import { SortDirection, SortField } from 'generated/graphql/schema';
import { usePage, Status as PageStatus } from 'page';
import { Direction, useSorting } from 'sorting';
import { Predicate } from 'utils';
import { Term, useSearchResultDisplay } from './useSearchResultDisplay';
import { useSearchCritiera } from './useSearchCriteria';

type Resolved<R> = {
    total: number;
    page: number;
    size?: number;
    content: R[];
};

type Results<R> = Resolved<R> & {
    terms: Term[];
};

type Waiting = { status: 'waiting' };

type Resetting = { status: 'resetting' };

type Initializing<C> = { status: 'initializing'; criteria: C };

type Requesting<C> = { status: 'requesting'; criteria: C };

type Fetching<A> = { status: 'fetching'; parameters: A; terms: Term[] };

type Completed<A, R> = { status: 'completed'; parameters: A; results: Results<R> };

type Failed = { status: 'error'; reason: string };

type NoInput<R> = { status: 'no-input'; results: Results<R> };

type State<C, A, R> =
    | Waiting
    | Initializing<C>
    | Resetting
    | Requesting<C>
    | Fetching<A>
    | Completed<A, R>
    | Failed
    | NoInput<R>;

type Action<C, A, R> =
    | { type: 'wait' }
    | { type: 'reset' }
    | { type: 'refresh' }
    | { type: 'initialize'; criteria: C }
    | { type: 'request'; criteria: C }
    | { type: 'fetch'; parameters: A; terms: Term[] }
    | { type: 'complete'; found: Resolved<R> }
    | { type: 'no-input'; parameters: A; page: number; size: number }
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
        return { status: 'resetting' };
    } else if (action.type === 'wait') {
        return { status: 'waiting' };
    } else if (action.type === 'no-input') {
        return {
            status: 'no-input',
            results: { total: 0, content: [], terms: [], page: action.page, size: action.size }
        };
    } else if (action.type === 'initialize') {
        return { status: 'initializing', criteria: action.criteria };
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

const defaultNoInputCheck: Predicate<Term[]> = (terms: Term[]) => terms.length === 0;

type SearchResulstInteraction<C, R> = {
    status: 'waiting' | 'resetting' | 'loading' | 'completed' | 'error' | 'no-input' | 'initializing';
    criteria?: C;
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

type SearchResultSettings<C, A, R> = {
    transformer: Tranformer<C, A>;
    resultResolver: ResultResolver<A, R>;
    termResolver: TermResolver<C>;
    defaultValues?: C;
    noInputCheck?: Predicate<Term[]>;
};

const useSearchResults = <C extends object, A extends object, R extends object>({
    transformer,
    resultResolver,
    termResolver,
    noInputCheck = defaultNoInputCheck,
    defaultValues
}: SearchResultSettings<C, A, R>): SearchResulstInteraction<C, R> => {
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

    const {
        criteria: searchCriteria,
        clear: clearCriteria,
        change: changeCriteria
    } = useSearchCritiera({ defaultValues });

    const searchResults = useSearchResultDisplay();

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
        if (state.status === 'fetching' || state.status === 'requesting') {
            searchResults.search();
        } else if (state.status === 'completed') {
            searchResults.complete(state.results.terms);
        } else if (state.status === 'resetting') {
            searchResults.reset();
        } else if (state.status === 'no-input') {
            searchResults.noInput();
        }
    }, [state.status, searchResults.search, searchResults.complete, searchResults.reset, searchResults.noInput]);

    const handleComplete = (resolved: Resolved<R>) => {
        ready(resolved.total, resolved.page + 1);
        dispatch({ type: 'complete', found: { ...resolved } });
    };

    const handleError = (error: Error) => dispatch({ type: 'error', reason: error.message });

    useEffect(() => {
        if (searchCriteria) {
            dispatch({ type: 'initialize', criteria: searchCriteria });
        }
    }, [searchCriteria, dispatch]);

    useEffect(() => {
        if (state.status === 'resetting') {
            clearCriteria();
        }
    }, [state.status, clearCriteria]);

    useEffect(() => {
        if (state.status === 'requesting') {
            const parameters = transformer(state.criteria);
            const terms = termResolver(state.criteria);

            if (noInputCheck(terms)) {
                dispatch({ type: 'no-input', parameters, page: page.current, size: page.pageSize });
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
                dispatch({ type: 'no-input', parameters, page: page.current, size: page.pageSize });
            } else {
                dispatch({ type: 'fetch', parameters, terms });
            }
        }
    }, [state.status, noInputCheck]);

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
            }).then(orElseEmptyResult(handleComplete), handleError);
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

    const status = useMemo(
        () => (state.status === 'fetching' || state.status === 'requesting' ? 'loading' : state.status),
        [state.status]
    );
    const criteria = useMemo(
        () => (state.status === 'initializing' || state.status === 'requesting' ? state.criteria : undefined),
        [state.status]
    );
    const results = useMemo(() => (state.status === 'completed' ? state.results : undefined), [state.status]);
    const error = useMemo(() => (state.status === 'error' ? state.reason : undefined), [state.status]);
    const reset = useCallback(() => dispatch({ type: 'reset' }), [dispatch]);
    const search = useCallback((criteria: C) => dispatch({ type: 'request', criteria }), [dispatch]);

    return {
        status,
        criteria,
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

export type { SearchResultSettings, ResultRequest, Resolved, SearchResulstInteraction };
export { useSearchResults };