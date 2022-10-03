import { DatePicker, Label } from '@trussworks/react-uswds';

type DatePickerProps = {
    id?: string;
    label?: string;
    name?: string;
    htmlFor?: string;
    onChange?: any;
    className?: string;
};

export const DatePickerInput = ({ id = '', name = '', label, htmlFor = '', onChange, className }: DatePickerProps) => {
    return (
        <>
            <Label htmlFor={htmlFor}>{label}</Label>
            <DatePicker id={id} onChange={onChange} className={className} name={name} />
        </>
    );
};
