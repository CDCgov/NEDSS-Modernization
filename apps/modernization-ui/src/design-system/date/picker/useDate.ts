import { useReducer } from 'react';
import { asStrictISODate } from 'design-system/date/asStrictISODate';

type State = { date?: string | undefined; value: string };
type Action = { type: 'initialize'; value?: string | null } | { type: 'change'; value: string } | { type: 'clear' };

const reducer = (current: State, action: Action): State => {
    switch (action.type) {
        case 'clear': {
            return { value: '' };
        }
        case 'change': {
            return { value: action.value, date: asStrictISODate(action.value) };
        }
        case 'initialize': {
            return action.value !== current.value ? asState(action.value ?? undefined) : current;
        }
        default:
            return current;
    }
};

const asState = (current?: string): State => {
    const date = asStrictISODate(current);
    const value = current ?? '';
    return { value, date };
};

type UseDateInteraction = {
    current: string;
    date?: string;
    initialize: (value?: string | null) => void;
    clear: () => void;
    change: (value: string) => void;
};

type UseDateSettings = {
    value?: string | null;
};

/**
 * Maintains a string representation of a date in a MM/DD/YYYY format with it's corresponding
 * value in the YYYY-MM-DD format.  Whenever the current value is in the MM/DD/YYYY format the
 * date is changed to the YYYY-MM-DD ISO date format.  If the current value is an invalid format the date
 * is undefined.
 *
 * Interactions:
 *
 *  current     - The MM/DD/YYYY value
 *  date        - The current value in the YYYY-MM-DD format
 *  initialize  - Initializes the value
 *  clear       - Clears the current value and the date value
 *  change      - Changes the current value, causing the date value to also update.
 *
 * @param {UseDateSettings} settings
 * @return {UseDateInteraction} The interaction with the hook
 */
const useDate = (settings?: UseDateSettings): UseDateInteraction => {
    const [state, dispatch] = useReducer(reducer, settings?.value ?? undefined, asState);

    const clear = () => dispatch({ type: 'clear' });
    const change = (value: string) => dispatch({ type: 'change', value });
    const initialize = (value?: string | null) => dispatch({ type: 'initialize', value });

    return {
        current: state.value,
        date: state.date,
        initialize,
        clear,
        change
    };
};

export { useDate };
