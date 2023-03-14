import { DatePicker, Label } from '@trussworks/react-uswds';
import './DatePickerInput.scss';
import { useState } from 'react';
import { isMatch } from 'date-fns/fp';

type OnChange = (val?: string) => void;

type DatePickerProps = {
    id?: string;
    label?: string;
    name?: string;
    htmlFor?: string;
    onChange?: OnChange;
    className?: string;
    defaultValue?: string;
};

const matches = isMatch('M/d/yyyy');

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
    className,
    defaultValue
}: DatePickerProps) => {
    const emptyDefaultValue = !defaultValue || defaultValue.length === 0;
    const validDefaultValue = !emptyDefaultValue && matches(defaultValue);
    const intialDefault = validDefaultValue ? interalize(defaultValue) : '';

    const [error, setError] = useState(!(emptyDefaultValue || validDefaultValue));

    const checkValidity = (fn?: OnChange) => (changed?: string) => {
        const valid = isValid(changed);
        setError(!valid);

        fn && fn(changed);
    };

    return (
        <div className={`date-picker-input ${error === true ? 'error' : ''}`}>
            {label && <Label htmlFor={htmlFor}>{label}</Label>}
            {error && <small className="text-red">{'Not a valid date'}</small>}
            <DatePicker
                id={id}
                onChange={checkValidity(onChange)}
                className={className}
                name={name}
                defaultValue={intialDefault}
            />
        </div>
    );
};
