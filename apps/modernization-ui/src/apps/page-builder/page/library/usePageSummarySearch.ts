import { useEffect, useReducer } from 'react';

import { PageSummary, PageSummaryService, Date, DateRange, MultiValue, SingleValue } from 'apps/page-builder/generated';
import { Status as PageStatus, usePagination } from 'pagination';
import { Filter, externalize } from 'filters';
import { useSorting } from 'sorting';

type Sorting = {
    property: string;
    direction: 'asc' | 'desc';
};

type Status = 'initialize' | 'idle' | 'searching' | 'found' | 'new-search';
type APIFilter = Date | DateRange | MultiValue | SingleValue;
type State = { status: Status; keyword?: string; filters: APIFilter[]; sorting?: Sorting; pages: PageSummary[] };

type Action =
    | { type: 'reset' }
    | { type: 'sort'; sorting: Sorting }
    | { type: 'search'; keyword?: string; filters: APIFilter[] }
    | { type: 'fetch'; keyword?: string; filters: APIFilter[] }
    | { type: 'found'; result: PageSummary[] }
    | { type: 'refresh' };

const reducer = (current: State, action: Action): State => {
    switch (action.type) {
        case 'sort':
            return { ...current, status: 'searching', sorting: action.sorting };
        case 'search':
            return { ...current, status: 'new-search', keyword: action.keyword, filters: action.filters, pages: [] };
        case 'fetch':
            return { ...current, status: 'searching', keyword: action.keyword, pages: [] };
        case 'found':
            return { ...current, status: 'found', pages: action.result };
        case 'refresh':
            return { ...current, status: 'searching' };
        case 'reset':
            return { ...current, status: 'idle' };
    }
};

const initial: State = {
    status: 'idle',
    filters: [],
    pages: [],
    keyword: ''
};

const usePageSummarySearch = () => {
    const [state, dispatch] = useReducer(reducer, initial);

    const { page, firstPage, ready } = usePagination();
    const { sorting } = useSorting();

    useEffect(() => {
        if (page.status == PageStatus.Requested) {
            dispatch({ type: 'refresh' });
        }
    }, [page.status, dispatch]);

    useEffect(() => {
        dispatch({ type: 'refresh' });
    }, [sorting, dispatch]);

    useEffect(() => {
        if (state.status === 'new-search') {
            if (page.current === 1) {
                dispatch({ type: 'fetch', keyword: state.keyword, filters: state.filters });
            } else {
                firstPage();
            }
        }
    }, [state.status]);

    useEffect(() => {
        if (state.status === 'new-search' && page.current == 1) {
            dispatch({ type: 'fetch', keyword: state.keyword, filters: state.filters });
        }
    }, [page.current]);

    useEffect(() => {
        if (state.status === 'searching') {
            PageSummaryService.search({
                requestBody: {
                    search: state.keyword,
                    filters: state.filters
                },
                page: page.current - 1,
                size: page.pageSize,
                sort: sorting ? [sorting] : undefined
            })
                .then((response) => ({
                    content: response.content ?? [],
                    total: response.totalElements ?? 0,
                    current: response.number ?? 0
                }))
                .then((result) => {
                    dispatch({ type: 'found', result: result.content });
                    ready(result.total, result.current + 1);
                });
        }
    }, [state.status, page.pageSize, page, state.keyword, state.filters, ready, sorting]);

    return {
        searching: state.status === 'searching',
        pages: state.pages,
        search: (keyword?: string, filters?: Filter[]) =>
            dispatch({ type: 'search', keyword, filters: externalize(filters ?? []) as APIFilter[] }),
        keyword: state.keyword
    };
};

export { usePageSummarySearch };
