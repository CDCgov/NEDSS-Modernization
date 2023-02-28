import { DatePicker, Label } from '@trussworks/react-uswds';
import './DatePickerInput.scss';
import { useEffect, useState } from 'react';
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
            if (defaultVal[0] === '') {
                setError(false);
            } else if (defaultVal.length === 3 && defaultVal[2].length === 4 && validateDate(defaultValue!)) {
                setError(false);
                setDefaultDate(`${defaultVal[2]}-${defaultVal[0]}-${defaultVal[1]}`);
            } else {
                setError(true);
            }
        } else {
            setDefaultDate('');
        }
    }, [defaultVal]);
    return (
        // <>
        <div className={`date-picker-input ${error === true ? 'error' : ''}`}>
            <Label htmlFor={htmlFor}>{label}</Label>
            {defaultDate && (
                <DatePicker defaultValue={defaultDate} id={id} onChange={onChange} className={className} name={name} />
            )}
            {!defaultDate && <DatePicker id={id} onChange={onChange} className={className} name={name} />}
        </div>
    );
};
