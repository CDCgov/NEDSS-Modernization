import { PageQuestionControllerService } from 'apps/page-builder/generated';
import { authorization } from 'authorization';
import { useEffect, useReducer } from 'react';

type State =
    | { status: 'idle' }
    | { status: 'validating'; page: number; questionId: number; dataMart: string }
    | { status: 'complete'; isValid: boolean }
    | { status: 'error'; error: string };

type Action =
    | { type: 'validate'; page: number; questionId: number; dataMart: string }
    | { type: 'complete'; isValid: boolean }
    | { type: 'error'; error: string };

const reducer = (_state: State, action: Action): State => {
    switch (action.type) {
        case 'validate':
            return {
                status: 'validating',
                page: action.page,
                questionId: action.questionId,
                dataMart: action.dataMart
            };
        case 'complete':
            return { status: 'complete', isValid: action.isValid };
        case 'error':
            return { status: 'error', error: action.error };
        default:
            return { status: 'idle' };
    }
};

export const usePageQuestionDataMartValidation = () => {
    const [state, dispatch] = useReducer(reducer, { status: 'idle' });

    useEffect(() => {
        if (state.status === 'validating') {
            PageQuestionControllerService.validateDatamartUsingGet({
                authorization: authorization(),
                page: state.page,
                questionId: state.questionId,
                datamart: state.dataMart
            })
                .then((response) => dispatch({ type: 'complete', isValid: response.isValid ?? false }))
                .catch(() => console.error(`Failed to validate Data Mart Column Name`));
        }
    }, [state.status]);

    const value = {
        error: state.status === 'error' ? state.error : undefined,
        isLoading: state.status === 'validating',
        isValid: state.status === 'complete' ? state.isValid : undefined,
        validate: (page: number, questionId: number, dataMart: string) =>
            dispatch({ type: 'validate', page, questionId, dataMart })
    };
    return value;
};
