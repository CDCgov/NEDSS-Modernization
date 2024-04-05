import { PageRuleControllerService, PagesSubSection, TargetSubsectionRequest } from 'apps/page-builder/generated';
import { useEffect, useReducer } from 'react';

type State =
    | { status: 'idle' }
    | { status: 'fetching'; id: number; request: TargetSubsectionRequest }
    | { status: 'complete'; subsections: PagesSubSection[] }
    | { status: 'error'; error: string };

type Action =
    | { type: 'fetch'; id: number; request: TargetSubsectionRequest }
    | { type: 'complete'; subsections: PagesSubSection[] }
    | { type: 'error'; error: string };

const reducer = (_state: State, action: Action): State => {
    switch (action.type) {
        case 'fetch':
            return { status: 'fetching', id: action.id, request: action.request };
        case 'complete':
            return { status: 'complete', subsections: action.subsections };
        case 'error':
            return { status: 'error', error: action.error };
        default:
            return { status: 'idle' };
    }
};

const initial: State = { status: 'idle' };

export const useGetTargetSubsections = () => {
    const [state, dispatch] = useReducer(reducer, initial);

    useEffect(() => {
        if (state.status === 'fetching') {
            PageRuleControllerService.getTargetSubsections({
                id: state.id,
                requestBody: state.request
            })
                .catch((error) => dispatch({ type: 'error', error: error.message }))
                .then((response) =>
                    response
                        ? dispatch({ type: 'complete', subsections: response })
                        : dispatch({ type: 'error', error: 'Failed to retrieve subsections' })
                );
        }
    }, [state.status]);

    const value = {
        error: state.status === 'error' ? state.error : undefined,
        response: state.status === 'complete' ? state.subsections : undefined,
        fetch: (id: number, request: TargetSubsectionRequest) => dispatch({ type: 'fetch', id, request })
    };

    return value;
};
