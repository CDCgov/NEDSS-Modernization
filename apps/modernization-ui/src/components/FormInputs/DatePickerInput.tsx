import { DatePicker } from '@trussworks/react-uswds';
import './DatePickerInput.scss';
import React, { KeyboardEvent, useState } from 'react';
import classNames from 'classnames';
import { isFuture } from 'date-fns';
import { EntryWrapper } from 'components/Entry';

type OnChange = (val?: string) => void;
type OnBlur = (event: React.FocusEvent<HTMLInputElement> | React.FocusEvent<HTMLDivElement>) => void;

type DatePickerProps = {
    id?: string;
    label?: string;
    name?: string;
    htmlFor?: string;
    onChange?: OnChange;
    onBlur?: OnBlur;
    className?: string;
    defaultValue?: string | null;
    errorMessage?: string;
    flexBox?: boolean;
    required?: boolean;
    disabled?: boolean;
    disableFutureDates?: boolean;
};

const inputFormat = /^[0-3]?[0-9]\/[0-3]?[0-9]\/(19|20)[0-9]{2}$/;

const matches = (value: string) => inputFormat.test(value);

const isValid = (value?: string) => !value || matches(value);

const interalize = (value?: string | null) => {
    if (value) {
        const [month, day, year] = value.split('/');
        return `${year}-${month}-${day}`;
    } else return undefined;
};

export const DatePickerInput = (props: DatePickerProps) => {
    const orientation = props.flexBox ? 'horizontal' : 'vertical';

    const emptyDefaultValue = !props.defaultValue || props.defaultValue.length === 0;
    const validDefaultValue = !emptyDefaultValue && props.defaultValue && matches(props.defaultValue);
    const intialDefault = validDefaultValue ? interalize(props.defaultValue) : undefined;

    const [error, setError] = useState(!(emptyDefaultValue || validDefaultValue));

    const checkValidity = (event: React.FocusEvent<HTMLInputElement> | React.FocusEvent<HTMLDivElement>) => {
        const currentVal = (event.target as HTMLInputElement).value;

        const valid = isValid(currentVal) && (!props.disableFutureDates || !isFuture(new Date(currentVal)));

        setError(!valid);
        props.onBlur && props.onBlur(event);
    };

    const _error = error
        ? 'Please enter a valid date (mm/dd/yyyy) using only numeric characters (0-9) or choose a date from the calendar by clicking on the calendar icon.'
        : props.errorMessage;

    return (
        <div className={classNames('date-picker-input', { error: _error })}>
            <EntryWrapper
                orientation={orientation}
                label={props.label || ''}
                htmlFor={props.htmlFor || ''}
                required={props.required}
                error={_error}>
                {props.defaultValue && (
                    <InternalDatePicker {...props} onBlur={checkValidity} defaultValue={intialDefault} />
                )}
                {!props.defaultValue && <InternalDatePicker {...props} onBlur={checkValidity} />}
            </EntryWrapper>
        </div>
    );
};

const InternalDatePicker = ({
    id = '',
    name = '',
    onChange,
    onBlur,
    className,
    defaultValue,
    disabled = false,
    disableFutureDates = false
}: DatePickerProps) => {
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
            id={id}
            onBlur={onBlur}
            onKeyDown={handleKeyDown}
            onChange={handleOnChange(onChange)}
            className={className}
            name={name}
            disabled={disabled}
            defaultValue={defaultValue || undefined}
            maxDate={disableFutureDates ? getCurrentLocalDate() : undefined}
        />
    );
};

const handleKeyDown = (event: KeyboardEvent<HTMLInputElement>) => {
    if (!isNaN(parseInt(event.key))) {
        // Keydown is triggered even before input's value is updated.
        // Hence the manual addition of the new key is required.
        let inputValue = `${(event.target as HTMLInputElement).value}${event.key}`;
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
    event.code === 'Enter' && event.preventDefault();
};
