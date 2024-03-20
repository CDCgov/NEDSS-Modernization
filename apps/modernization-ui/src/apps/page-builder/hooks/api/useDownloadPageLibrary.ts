import { OpenAPI } from 'apps/page-builder/generated/core/OpenAPI';
import { authorization } from 'authorization';
import { useEffect, useReducer } from 'react';
import { BusinessRuleSort } from './useFetchPageRules';

type State =
    | { status: 'idle' }
    | { status: 'downloading'; page: number; sort: BusinessRuleSort | undefined; query: string; format: 'csv' | 'pdf' }
    | { status: 'complete' }
    | { status: 'error'; error: string };

type Action =
    | { type: 'download'; page: number; sort: BusinessRuleSort | undefined; query: string; format: 'csv' | 'pdf' }
    | { type: 'complete' }
    | { type: 'error'; error: string };

const reducer = (_state: State, action: Action): State => {
    switch (action.type) {
        case 'download':
            return {
                status: 'downloading',
                page: action.page,
                sort: action.sort,
                query: action.query,
                format: action.format
            };
        case 'complete':
            return { status: 'complete' };
        case 'error':
            return { status: 'error', error: action.error };
        default:
            return { status: 'idle' };
    }
};

export const useDownloadPageLibrary = () => {
    const [state, dispatch] = useReducer(reducer, { status: 'idle' });

    useEffect(() => {
        if (state.status === 'downloading') {
            const sortString = state.sort ? `${state.sort.field},${state.sort.direction}` : undefined;
            if (state.format === 'pdf') {
                downloadPdf(state.page, sortString, state.query);
            } else if (state.format === 'csv') {
                downloadCsv(state.page, sortString, state.query);
            }
        }
    }, [state.status]);

    const downloadPdf = (page: number, sort: string | undefined, query: string) => {
        // auto generated methods dont allow direct conversion to blob
        fetch(`${OpenAPI.BASE}/nbs/page-builder/api/v1/pages/${page}/rules/pdf`, {
            method: 'POST',
            headers: {
                Accept: 'application/pdf',
                'Content-Type': 'application/json',
                Authorization: authorization()
            },
            body: JSON.stringify({
                pageId: page,
                page: 0,
                pageSize: 1000,
                sort: sort,
                query: query
            })
        })
            .then((response) => response.blob())
            .then((blob) => {
                const url = window.URL.createObjectURL(blob);
                const a = document.createElement('a');
                a.href = url;
                a.download = 'ManageRulesLibrary.pdf';
                a.click();
                dispatch({ type: 'complete' });
            })
            .catch((error) => dispatch({ type: 'error', error: error.message }));
    };

    const downloadCsv = (page: number, sort: string | undefined, query: string) => {
        // auto generated methods dont allow direct conversion to blob
        fetch(`${OpenAPI.BASE}/nbs/page-builder/api/v1/pages/${page}/rules/csv`, {
            method: 'POST',
            headers: {
                Accept: 'application/csv',
                'Content-Type': 'application/json',
                Authorization: authorization()
            },
            body: JSON.stringify({
                pageId: page,
                page: 0,
                pageSize: 1000,
                sort: sort,
                query: query
            })
        })
            .then((response) => response.blob())
            .then((blob) => {
                const url = window.URL.createObjectURL(blob);
                const a = document.createElement('a');
                a.href = url;
                a.download = 'ManageRulesLibrary.csv';
                a.click();
                dispatch({ type: 'complete' });
            })
            .catch((error) => dispatch({ type: 'error', error: error.message }));
    };

    const value = {
        error: state.status === 'error' ? state.error : undefined,
        isLoading: state.status === 'downloading',
        downloadPdf: (page: number, sort: BusinessRuleSort | undefined, query: string) =>
            dispatch({ type: 'download', page, sort, query, format: 'pdf' }),
        downloadCsv: (page: number, sort: BusinessRuleSort | undefined, query: string) =>
            dispatch({ type: 'download', page, sort, query, format: 'csv' })
    };

    return value;
};
