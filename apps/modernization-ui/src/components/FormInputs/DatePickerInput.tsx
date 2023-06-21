import { DatePicker, Grid, Label } from '@trussworks/react-uswds';
import './DatePickerInput.scss';
import React, { useState } from 'react';

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
    defaultValue?: string;
    errorMessage?: string;
    flexBox?: boolean;
    disabled?: boolean;
};

const inputFormat = /^[0-3]?[0-9]\/[0-3]?[0-9]\/[0-9]{4}$/;

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
    disabled = false
}: DatePickerProps) => {
    const emptyDefaultValue = !defaultValue || defaultValue.length === 0;
    const validDefaultValue = !emptyDefaultValue && matches(defaultValue);
    const intialDefault = validDefaultValue ? interalize(defaultValue) : '';

    const [error, setError] = useState(!(emptyDefaultValue || validDefaultValue));

    const checkValidity = (event: React.FocusEvent<HTMLInputElement> | React.FocusEvent<HTMLDivElement>) => {
        const currentVal = (event.target as HTMLInputElement).value;
        const valid = isValid(currentVal);
        setError(!valid);
        onBlur && onBlur(event);
    };

    const handleOnChange = (fn?: OnChange) => (changed?: string) => {
        const valid = isValid(changed);
        valid && fn && fn(changed);
    };

    return !flexBox ? (
        <div className={`date-picker-input ${error === true ? 'error' : ''}`}>
            {label && <Label htmlFor={htmlFor}>{label}</Label>}
            {error && <small className="text-red">{'Not a valid date'}</small>}
            {!intialDefault && (
                <DatePicker
                    id={id}
                    onBlur={checkValidity}
                    onChange={handleOnChange(onChange)}
                    className={className}
                    disabled={disabled}
                    name={name}
                />
            )}
            {intialDefault && (
                <DatePicker
                    id={id}
                    onBlur={checkValidity}
                    onChange={handleOnChange(onChange)}
                    className={className}
                    name={name}
                    disabled={disabled}
                    defaultValue={intialDefault}
                />
            )}
        </div>
    ) : (
        <Grid row className={`date-picker-input ${error === true ? 'error' : ''}`}>
            <Grid col={6}>{label && <Label htmlFor={htmlFor}>{label}</Label>}</Grid>
            <Grid col={6}>
                {error && <small className="text-red">{'Not a valid date'}</small>}
                {!intialDefault && (
                    <DatePicker
                        id={id}
                        onBlur={checkValidity}
                        onChange={handleOnChange(onChange)}
                        className={className}
                        disabled={disabled}
                        name={name}
                    />
                )}
                {intialDefault && (
                    <DatePicker
                        id={id}
                        onBlur={checkValidity}
                        onChange={handleOnChange(onChange)}
                        className={className}
                        name={name}
                        disabled={disabled}
                        defaultValue={intialDefault}
                    />
                )}
            </Grid>
        </Grid>
    );
};
