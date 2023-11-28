/* eslint-disable camelcase */
import { useContext, useEffect, useReducer } from 'react';
import { UserContext } from 'user';
import { AddQuestionResponse, PageQuestionControllerService } from '../../generated';

type State =
    | { status: 'idle' }
    | { status: 'adding'; request: { questions: number[]; subsection: number; page: number } }
    | { status: 'complete'; response: AddQuestionResponse }
    | { status: 'error'; error: string };

type Action =
    | { type: 'add'; add: { questions: number[]; subsection: number; page: number } }
    | { type: 'complete'; response: AddQuestionResponse }
    | { type: 'error'; error: string };

const initial: State = { status: 'idle' };

const reducer = (_state: State, action: Action): State => {
    switch (action.type) {
        case 'add':
            return { status: 'adding', request: action.add };
        case 'complete':
            return { status: 'complete', response: action.response };
        case 'error':
            return { status: 'error', error: action.error };
        default:
            return { ...initial };
    }
};

export const useAddQuestionsToPage = () => {
    const {
        state: { getToken }
    } = useContext(UserContext);
    const [state, dispatch] = useReducer(reducer, initial);

    useEffect(() => {
        if (state.status === 'adding') {
            PageQuestionControllerService.addQuestionToPageUsingPost({
                authorization: `Bearer ${getToken()}`,
                request: { questionIds: state.request.questions },
                page: state.request.page,
                subsection: state.request.subsection
            })
                .catch((error) => dispatch({ type: 'error', error: error.message }))
                .then((response) => dispatch({ type: 'complete', response: response as AddQuestionResponse }));
        }
    }, [state.status]);

    const value = {
        error: state.status === 'error' ? state.error : undefined,
        isLoading: state.status === 'adding',
        response: state.status === 'complete' ? state.response : undefined,
        add: (questions: number[], subsection: number, page: number) =>
            dispatch({ type: 'add', add: { questions, subsection, page } })
    };

    return value;
};
