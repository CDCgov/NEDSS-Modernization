import { useEffect, useReducer } from 'react';

import {
    GroupSubSectionRequest,
    SubSectionControllerService,
    UpdateSubSectionRequest
} from 'apps/page-builder/generated';
import { authorization } from 'authorization';

export type GroupRequest = GroupSubSectionRequest & UpdateSubSectionRequest;

type State =
    | { status: 'idle' }
    | { status: 'grouping'; page: number; subsection: number; request: GroupRequest }
    | { status: 'complete' }
    | { status: 'error'; error: string };

type Action =
    | { type: 'group'; page: number; subsection: number; request: GroupRequest }
    | { type: 'complete' }
    | { type: 'error'; error: string };

const initial: State = { status: 'idle' };

const reducer = (_state: State, action: Action): State => {
    switch (action.type) {
        case 'group':
            return {
                status: 'grouping',
                page: action.page,
                subsection: action.subsection,
                request: action.request
            };
        case 'complete':
            return { status: 'complete' };
        case 'error':
            return { status: 'error', error: action.error };
        default:
            return { ...initial };
    }
};

export const useGroupSubsection = () => {
    const [state, dispatch] = useReducer(reducer, initial);

    useEffect(() => {
        if (state.status === 'grouping') {
            // group allows editing the subsection as well as performing the actual grouping
            SubSectionControllerService.updateSubSectionUsingPut({
                authorization: authorization(),
                page: state.page,
                subSectionId: state.subsection,
                request: state.request
            })
                .then(() => {
                    SubSectionControllerService.groupSubSectionUsingPost({
                        authorization: authorization(),
                        page: state.page,
                        subsection: state.subsection,
                        request: state.request
                    })
                        .then(() => dispatch({ type: 'complete' }))
                        .catch((error) => dispatch({ type: 'error', error: error.message }));
                })
                .catch((error) => dispatch({ type: 'error', error: error.message }));
        }
    }, [state.status]);

    const value = {
        error: state.status === 'error' ? state.error : undefined,
        isLoading: state.status === 'grouping',
        response: state.status === 'complete',
        group: (page: number, subsection: number, request: GroupRequest) =>
            dispatch({ type: 'group', page, subsection, request })
    };

    return value;
};
