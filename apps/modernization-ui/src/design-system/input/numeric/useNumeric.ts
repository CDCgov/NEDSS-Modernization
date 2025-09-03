import { useReducer } from 'react';

type NumericValue = number | undefined | null;
type State = { value?: NumericValue };
type Action = { type: 'initialize'; value: NumericValue } | { type: 'change'; value: number } | { type: 'clear' };

const reducer = (current: State, action: Action): State => {
    switch (action.type) {
        case 'change': {
            return { value: action.value };
        }
        case 'clear': {
            return { value: undefined };
        }
        case 'initialize': {
            return action.value !== current.value ? { value: action.value ?? undefined } : current;
        }
        default:
            return current;
    }
};

type UseNumericInteraction = {
    current: NumericValue;
    initialize: (value: NumericValue) => void;
    change: (value: number) => void;
    clear: () => void;
};

const useNumeric = (value?: NumericValue): UseNumericInteraction => {
    const [state, dispatch] = useReducer(reducer, { value });

    const clear = () => dispatch({ type: 'clear' });
    const change = (value: number) => dispatch({ type: 'change', value });
    const initialize = (value: NumericValue) => dispatch({ type: 'initialize', value });

    return {
        current: state.value,
        initialize,
        change,
        clear
    };
};

export { useNumeric };
