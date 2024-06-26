import { OpenAPI } from 'apps/page-builder/generated/core/OpenAPI';
import { useEffect, useReducer } from 'react';

type State =
    | { status: 'idle' }
    | { status: 'downloading'; page: number }
    | { status: 'complete' }
    | { status: 'error'; error: string };

type Action = { type: 'download'; page: number } | { type: 'complete' } | { type: 'error'; error: string };

const reducer = (_state: State, action: Action): State => {
    switch (action.type) {
        case 'download':
            return { status: 'downloading', page: action.page };
        case 'complete':
            return { status: 'complete' };
        case 'error':
            return { status: 'error', error: action.error };
        default:
            return { status: 'idle' };
    }
};

export const useDownloadPageMetadata = () => {
    const [state, dispatch] = useReducer(reducer, { status: 'idle' });

    useEffect(() => {
        if (state.status === 'downloading') {
            // auto generated methods dont allow direct conversion to blob
            fetch(`${OpenAPI.BASE}/api/v1/pages/${state.page}/metadata`, {
                method: 'GET',
                headers: {
                    Accept: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet',
                    'Content-Type': 'application/json'
                }
            })
                .then((response) => response.blob())
                .then((blob) => {
                    const url = window.URL.createObjectURL(blob);
                    const a = document.createElement('a');
                    a.href = url;
                    a.download = 'PageMetadata.xlsx';
                    a.click();
                    dispatch({ type: 'complete' });
                })
                .catch((error) => dispatch({ type: 'error', error: error.message }));
        }
    }, [state.status]);

    const value = {
        error: state.status === 'error' ? state.error : undefined,
        isLoading: state.status === 'downloading',
        downloadMetadata: (page: number) => dispatch({ type: 'download', page })
    };

    return value;
};
