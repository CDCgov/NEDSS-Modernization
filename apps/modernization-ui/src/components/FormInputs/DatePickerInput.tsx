import { DatePicker, Label } from '@trussworks/react-uswds';
import './DatePickerInput.scss';
import React, { useState, useEffect } from 'react';
import { validateDate } from '../../utils/DateValidation';

type DatePickerProps = {
    id?: string;
    label?: string;
    name?: string;
    htmlFor?: string;
    onChange?: any;
    className?: string;
    defaultValue?: string;
};

export const DatePickerInput = ({
    id = '',
    name = '',
    label,
    htmlFor = '',
    onChange,
    className,
    defaultValue
}: DatePickerProps) => {
    const defaultVal: any = defaultValue?.split('/');
    const [defaultDate, setDefaultDate] = useState('');
    const [error, setError] = useState(false);

    useEffect(() => {
        if (defaultVal) {
            setDefaultDate(`${defaultVal[2]}-${defaultVal[0]}-${defaultVal[1]}`);
        }
    });

    const validateDatePicker = (event: React.FocusEvent<HTMLInputElement> | React.FocusEvent<HTMLDivElement>) => {
        if (defaultVal) {
            if (defaultVal[0] === '') {
                setError(false);
                setDefaultDate('');
            } else if (defaultVal.length === 3 && defaultVal[2].length === 4 && validateDate(defaultValue!)) {
                setDefaultDate(`${defaultVal[2]}-${defaultVal[0]}-${defaultVal[1]}`);
                setError(false);
            } else {
                setError(true);
            }
        } else {
            const currentVal = (event.target as HTMLInputElement).value;
            if (!currentVal || validateDate(currentVal)) {
                setError(false);
            } else {
                setError(true);
            }
        }
    };
    return (
        <div className={`date-picker-input ${error === true ? 'error' : ''}`}>
            {label && <Label htmlFor={htmlFor}>{label}</Label>}
            {error && <small className="text-red">{'Not a valid date'}</small>}
            {defaultDate && (
                <DatePicker
                    defaultValue={defaultDate}
                    id={id}
                    onChange={onChange}
                    className={className}
                    name={name}
                    onBlur={validateDatePicker}
                />
            )}
            {!defaultDate && (
                <DatePicker id={id} onChange={onChange} className={className} name={name} onBlur={validateDatePicker} />
            )}
        </div>
    );
};
