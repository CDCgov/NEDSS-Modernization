import { DatePicker } from '@trussworks/react-uswds';
import { FocusEvent as ReactFocusEvent, KeyboardEvent as ReactKeyboardEvent, useState } from 'react';
import classNames from 'classnames';
import { isFuture } from 'date-fns';
import { EntryWrapper, Orientation, Sizing } from 'components/Entry';
import { EN_US } from './datePickerLocalization';

type OnChange = (val?: string) => void;
type OnBlur = (event: ReactFocusEvent<HTMLInputElement> | ReactFocusEvent<HTMLDivElement>) => void;

type DatePickerProps = {
    label?: string;
    helperText?: string;
    name?: string;
    onChange?: OnChange;
    onBlur?: OnBlur;
    className?: string;
    defaultValue?: string | null;
    errorMessage?: string;
    flexBox?: boolean;
    error?: string;
    orientation?: Orientation;
    sizing?: Sizing;
    required?: boolean;
    disabled?: boolean;
    disableFutureDates?: boolean;
};

const inputFormat = /^[0-3]?[0-9]\/[0-3]?[0-9]\/(19|20)[0-9]{2}$/;
const isNumber = /^[0-9/]$/;
let removedSlash = false;

const matches = (value: string) => inputFormat.test(value);

const isValid = (value?: string) => !value || matches(value);

const interalize = (value?: string | null) => {
    if (value) {
        const [month, day, year] = value.split('/');
        return `${year}-${month}-${day}`;
    } else return undefined;
};

export const DatePickerInput = (props: DatePickerProps) => {
    const orientation = props.flexBox ? 'horizontal' : props.orientation;

    const emptyDefaultValue = !props.defaultValue || props.defaultValue.length === 0;
    const validDefaultValue = !emptyDefaultValue && props.defaultValue && matches(props.defaultValue);
    const intialDefault = validDefaultValue ? interalize(props.defaultValue) : undefined;

    const [error, setError] = useState(!(emptyDefaultValue || validDefaultValue) && props.defaultValue !== 'none');

    const checkValidity = (event: ReactFocusEvent<HTMLInputElement> | ReactFocusEvent<HTMLDivElement>) => {
        const currentVal = (event.target as HTMLInputElement).value;

        const valid = isValid(currentVal) && (!props.disableFutureDates || !isFuture(new Date(currentVal)));

        setError(!valid);
        props.onBlur && props.onBlur(event);
    };

    const _error = error
        ? 'Please enter a valid date (mm/dd/yyyy) using only numeric characters (0-9) or choose a date from the calendar by clicking on the calendar icon.'
        : props.errorMessage;

    return (
        <EntryWrapper
            orientation={orientation}
            sizing={props.sizing}
            label={props.label || ''}
            helperText={props.helperText}
            htmlFor={props.name || ''}
            required={props.required}
            error={_error}>
            {props.defaultValue && (
                <InternalDatePicker {...props} error={_error} onBlur={checkValidity} defaultValue={intialDefault} />
            )}
            {!props.defaultValue && <InternalDatePicker {...props} error={_error} onBlur={checkValidity} />}
        </EntryWrapper>
    );
};

const InternalDatePicker = ({
    name = '',
    onChange,
    onBlur,
    className,
    defaultValue,
    disabled = false,
    disableFutureDates = false,
    label,
    error
}: DatePickerProps) => {
    const toggleCalendar = label ? `${label} toggle calendar` : EN_US.toggleCalendar;
    const getCurrentLocalDate = () => {
        let currentDate = new Date();
        const offset = currentDate.getTimezoneOffset() * 60 * 1000;
        currentDate = new Date(currentDate.getTime() - offset);
        return currentDate.toISOString();
    };

    const handleOnChange = (fn?: OnChange) => (changed?: string) => {
        const valid = isValid(changed);
        valid && fn && fn(changed);
    };

    //  In order for the defaultValue to be applied the component has to be re-created when it goes from null to non null.
    return (
        <DatePicker
            i18n={{ ...EN_US, toggleCalendar }}
            id={name}
            onBlur={onBlur}
            onKeyDown={handleKeyDown}
            onChange={handleOnChange(onChange)}
            className={classNames(className)}
            validationStatus={error ? 'error' : undefined}
            name={name}
            disabled={disabled}
            defaultValue={defaultValue || undefined}
            maxDate={disableFutureDates ? getCurrentLocalDate() : undefined}
        />
    );
};

const handleKeyDown = (event: ReactKeyboardEvent<HTMLInputElement>) => {
    const allowedKeys = [
        '0',
        '1',
        '2',
        '3',
        '4',
        '5',
        '6',
        '7',
        '8',
        '9',
        'Backspace',
        'ArrowLeft',
        'ArrowRight',
        'Delete',
        'Tab',
        'Shift'
    ];

    const key = event.key;
    let inputValue = '';

    if (allowedKeys.indexOf(key) === -1) {
        event.preventDefault();
    } else {
        // Keydown is triggered even before input's value is updated.
        // Hence the manual addition of the new key is required.

        inputValue = `${(event.target as HTMLInputElement).value}`;
        // check if key is a number or "/"
        if (isNumber.test(key)) {
            if (removedSlash) {
                inputValue += `/${key}`;
                (event.target as HTMLInputElement).value = inputValue;
                removedSlash = false;
                event.preventDefault();
            } else {
                inputValue = `${(event.target as HTMLInputElement).value}${key}`;
                if (
                    inputValue &&
                    (inputValue.length === 2 ||
                        (inputValue.length === 5 && (inputValue.match(new RegExp('/', 'g')) || '').length < 2))
                ) {
                    inputValue += '/';
                    (event.target as HTMLInputElement).value = inputValue;
                    // This prevent default ensures the manually entered key is not re-entered.
                    event.preventDefault();
                }
            }
        }
        if (key === 'Backspace') {
            removedSlash = inputValue.endsWith('/');
        }
    }
};
