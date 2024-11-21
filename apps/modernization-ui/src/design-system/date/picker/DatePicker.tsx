import { useEffect, useLayoutEffect, useRef, ChangeEvent as ReactChangeEvent, useReducer } from 'react';
import { mapOr } from 'utils/mapping';
import { asDateEntry, asISODate } from 'design-system/date/entry';
import { maskedAsDate } from './maskedAsDate';

import datePicker from '@uswds/uswds/js/usa-date-picker';

type State = { date?: string | undefined; value: string };
type Action = { type: 'change'; value: string } | { type: 'clear' };

const reducer = (current: State, action: Action): State => {
    switch (action.type) {
        case 'clear': {
            return { value: '' };
        }
        case 'change': {
            return { value: action.value, date: asStrictISODate(action.value) };
        }
        default:
            return current;
    }
};

const initialize = (current?: string): State => {
    const date = asStrictISODate(current);
    const value = current ?? '';
    return { date, value };
};

const isNonNumericKey = (key: string) => {
    return key.length == 1 ? !/[0-9]/.test(key) : false;
};

const handleExternalKeyDown = (event: Event) => {
    if (event instanceof KeyboardEvent) {
        if (!event.altKey && !event.ctrlKey && !event.metaKey && isNonNumericKey(event.key)) {
            //  prevents spaces and letter characters from being entered but allows keyboard short cuts like (crtl-v)
            event.preventDefault();
        }
    }
};

const handleExternalKeyUp = (event: Event) => {
    const input = event.target as HTMLInputElement;

    input.value = maskedAsDate(input.value);
};

type TextualDate = string | undefined;

const asStrictISODate = (value: TextualDate): string | undefined => {
    const entry = asDateEntry(value);
    return asISODate(entry);
};

const today = () => new Date().toISOString().substring(0, 10);

const asDateOrToday = mapOr(asStrictISODate, today);

type DatePickerProps = {
    id: string;
    label?: string;
    minDate?: TextualDate;
    maxDate?: TextualDate;
    value?: TextualDate;
    onChange?: (value?: string) => void;
} & Omit<JSX.IntrinsicElements['input'], 'defaultValue' | 'onChange' | 'value' | 'type' | 'inputMode'>;

const DatePicker = ({ id, label, maxDate, minDate, value, onChange, ...remaining }: DatePickerProps) => {
    const [state, dispatch] = useReducer(reducer, value, initialize);

    const datePickerRef = useRef<HTMLDivElement>(null);
    const externalInputRef = useRef<HTMLInputElement | null>(null);

    const adjustedMaxValue = asDateOrToday(maxDate);
    const adjustedMinValue = asStrictISODate(minDate);

    useEffect(() => {
        if (value) {
            dispatch({ type: 'change', value });
        } else {
            dispatch({ type: 'clear' });
        }
    }, [value]);

    const handleExternalOnChange = (event: Event) => {
        if (event.target && 'value' in event.target && typeof event.target.value === 'string') {
            dispatch({ type: 'change', value: event.target.value });
        } else {
            dispatch({ type: 'clear' });
        }
    };

    const handleInternalOnChange = (event: ReactChangeEvent<HTMLInputElement>) => {
        dispatch({ type: 'change', value: event.target.value });
    };

    useLayoutEffect(() => {
        const datePickerElement = datePickerRef.current as HTMLDivElement;
        const wrapper = datePickerElement.querySelector('.usa-date-picker__wrapper');

        datePicker.on(datePickerElement);

        const context = datePicker.getDatePickerContext(datePickerElement);

        if (label) {
            const toggle = context.toggleBtnEl as HTMLButtonElement;

            toggle.ariaLabel = `Toggle ${label} calendar`;
        }
        const external = context.externalInputEl as HTMLInputElement;
        externalInputRef.current = external;

        external.addEventListener('change', handleExternalOnChange);
        external.addEventListener('keyup', handleExternalKeyUp);
        external.addEventListener('keydown', handleExternalKeyDown);

        return () => {
            external.removeEventListener('change', handleExternalOnChange);
            external.removeEventListener('keyup', handleExternalKeyUp);
            external.removeEventListener('keydown', handleExternalKeyDown);

            if (wrapper) {
                datePicker.off(datePickerElement);
            }
        };
    }, []);

    useEffect(() => {
        if (externalInputRef.current) {
            externalInputRef.current.value = state.value;
        }
        onChange?.(state.value);
    }, [state.value]);

    return (
        <div
            ref={datePickerRef}
            className="usa-date-picker"
            data-default-value={state.date}
            data-max-date={adjustedMaxValue}
            data-min-date={adjustedMinValue}>
            <input
                id={id}
                className="usa-input"
                type="text"
                value={state.value}
                onChange={handleInternalOnChange}
                {...remaining}
            />
        </div>
    );
};

export { DatePicker };
export type { DatePickerProps };
