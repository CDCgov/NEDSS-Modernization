import { DatePicker } from '@trussworks/react-uswds';
import './DatePickerInput.scss';
import React, { useState } from 'react';
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

const interalize = (value: string) => {
    const [month, day, year] = value.split('/');
    return `${year}-${month}-${day}`;
};

export const DatePickerInput = ({
    id = '',
    name = '',
    label,
    htmlFor = '',
    onChange,
    onBlur,
    className,
    defaultValue,
    flexBox,
    errorMessage,
    required,
    disabled = false,
    disableFutureDates = false
}: DatePickerProps) => {
    const emptyDefaultValue = !defaultValue || defaultValue.length === 0;
    const validDefaultValue = !emptyDefaultValue && matches(defaultValue);
    const intialDefault = validDefaultValue ? interalize(defaultValue) : undefined;

    const [error, setError] = useState(!(emptyDefaultValue || validDefaultValue));

    const getCurrentLocalDate = () => {
        let currentDate = new Date();
        const offset = currentDate.getTimezoneOffset() * 60 * 1000;
        currentDate = new Date(currentDate.getTime() - offset);
        return currentDate.toISOString();
    };

    const checkValidity = (event: React.FocusEvent<HTMLInputElement> | React.FocusEvent<HTMLDivElement>) => {
        const currentVal = (event.target as HTMLInputElement).value;

        const valid = isValid(currentVal) && (!disableFutureDates || !isFuture(new Date(currentVal)));

        setError(!valid);
        onBlur && onBlur(event);
    };

    const handleOnChange = (fn?: OnChange) => (changed?: string) => {
        const valid = isValid(changed);
        valid && fn && fn(changed);
    };

    const orientation = flexBox ? 'horizontal' : 'vertical';

    const _error = error
        ? 'Please enter a valid date (mm/dd/yyyy) using only numeric characters (0-9) or choose a date from the calendar by clicking on the calendar icon.'
        : errorMessage;

    return (
        <div className={classNames('date-picker-input', { error: _error })}>
            <EntryWrapper
                orientation={orientation}
                label={label || ''}
                htmlFor={htmlFor || ''}
                required={required}
                error={_error}>
                <DatePicker
                    id={id}
                    onBlur={checkValidity}
                    onKeyDown={(e) => e.code === 'Enter' && e.preventDefault()}
                    onChange={handleOnChange(onChange)}
                    className={className}
                    name={name}
                    disabled={disabled}
                    defaultValue={intialDefault}
                    maxDate={disableFutureDates ? getCurrentLocalDate() : undefined}
                />
            </EntryWrapper>
        </div>
    );
};
