import { DatePicker, Label } from '@trussworks/react-uswds';
import { useEffect, useState } from 'react';

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
    useEffect(() => {
        if (defaultVal) {
            setDefaultDate(`${defaultVal[2]}-${defaultVal[0]}-${defaultVal[1]}`);
        } else {
            setDefaultDate('');
        }
    }, [defaultVal]);
    return (
        <>
            {label && <Label htmlFor={htmlFor}>{label}</Label>}
            {defaultDate && (
                <DatePicker defaultValue={defaultDate} id={id} onChange={onChange} className={className} name={name} />
            )}
            {!defaultDate && <DatePicker id={id} onChange={onChange} className={className} name={name} />}
        </>
    );
};
