import { useReducer } from 'react';
import { DateCriteria, DateEqualsCriteria } from '../entry';

export type ExactDateEntryFields = 'day' | 'month' | 'year';
export type BetweenDateEntryFields = 'from' | 'to';

export type DateEntryFields = ExactDateEntryFields | BetweenDateEntryFields;

type Action =
    | { type: 'apply'; field: DateEntryFields; value: string }
    | { type: 'updateDateCriteria'; value: DateCriteria }
    | { type: 'clear'; field: DateEntryFields };

const dateReducer = (state: DateCriteria, action: Action): DateCriteria => {
    switch (action.type) {
        case 'apply':
            if ('equals' in state) {
                return {
                    equals: { ...state.equals, [action.field]: action.value ? parseInt(action.value) : '' }
                };
            } else if ('between' in state) {
                return {
                    between: { ...state.between, [action.field]: action.value }
                };
            }
            break;
        case 'updateDateCriteria':
            return {
                ...action.value
            };
        case 'clear':
            if ('equals' in state) {
                const newState = { equals: { ...state.equals } };
                delete newState.equals[action.field as ExactDateEntryFields];
                return newState;
            } else if ('between' in state) {
                const newState = { between: { ...state.between } };
                delete newState.between[action.field as BetweenDateEntryFields];
                return newState;
            }
            break;
        default:
            return state;
    }
    return state;
};

export const useDateEqualsCriteria = (initialValue: DateEqualsCriteria) => {
    const [state, dispatch] = useReducer(dateReducer, initialValue);

    const apply = (field: DateEntryFields, value: string) => {
        dispatch({ type: 'apply', field, value });
    };

    const updateDateCriteria = (value: DateCriteria) => {
        dispatch({ type: 'updateDateCriteria', value });
    };

    const clear = (field: DateEntryFields) => {
        dispatch({ type: 'clear', field });
    };

    return { state, apply, updateDateCriteria, clear };
};
