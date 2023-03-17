import { DatePicker, Grid, Label } from '@trussworks/react-uswds';
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
    errorMessage?: string;
    flexBox?: boolean;
};

export const DatePickerInput = ({
    id = '',
    name = '',
    label,
    htmlFor = '',
    onChange,
    className,
    defaultValue,
    errorMessage,
    flexBox
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
    return !flexBox ? (
        <div className={`date-picker-input ${error === true ? 'error' : ''}`}>
            {label && <Label htmlFor={htmlFor}>{label}</Label>}
            {error && <small className="text-red">{'Not a valid date'}</small>}
            {defaultDate && (
                <DatePicker defaultValue={defaultDate} id={id} onChange={onChange} className={className} name={name} />
            )}
            {!defaultDate && <DatePicker id={id} onChange={onChange} className={className} name={name} />}
            <p className="text-red margin-y-1">{errorMessage}</p>
        </div>
    ) : (
        <Grid row className={`date-picker-input ${error === true ? 'error' : ''}`}>
            <Grid col={6}>{label && <Label htmlFor={htmlFor}>{label}</Label>}</Grid>
            <Grid col={6}>
                {error && <small className="text-red">{'Not a valid date'}</small>}
                {defaultDate && (
                    <DatePicker
                        defaultValue={defaultDate}
                        id={id}
                        onChange={onChange}
                        className={className}
                        name={name}
                    />
                )}
                {!defaultDate && <DatePicker id={id} onChange={onChange} className={className} name={name} />}
                <p className="text-red margin-y-1">{errorMessage}</p>
            </Grid>
        </Grid>
    );
};
