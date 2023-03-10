import { DatePicker, Label } from '@trussworks/react-uswds';
import './DatePickerInput.scss';
import { useEffect, useState } from 'react';
import { validateDate } from '../../utils/DateValidation';

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
                setDefaultDate('');
            } else if (defaultVal.length === 3 && defaultVal[2].length === 4 && validateDate(defaultValue!)) {
                setDefaultDate(`${defaultVal[2]}-${defaultVal[0]}-${defaultVal[1]}`);
                setError(false);
            } else {
                setError(true);
                setDefaultDate(`${defaultVal[2]}-${defaultVal[0]}-${defaultVal[1]}`);
            }
        } else {
            setDefaultDate('0-0-0');
        }
    }, [defaultVal]);
    return (
        <div className={`date-picker-input ${error === true ? 'error' : ''}`}>
            {label && <Label htmlFor={htmlFor}>{label}</Label>}
            {error && <small className="text-red">{'Not a valid date'}</small>}
            {defaultDate && (
                <DatePicker defaultValue={defaultDate} id={id} onChange={onChange} className={className} name={name} />
            )}
            {!defaultDate && <DatePicker id={id} onChange={onChange} className={className} name={name} />}
        </div>
    );
};
