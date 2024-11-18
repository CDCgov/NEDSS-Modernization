import { EntryWrapper, EntryComponentProps } from 'design-system/entry/base';
import { DatePicker, DatePickerProps } from './DatePicker';

type DatePickerInputProps = EntryComponentProps & DatePickerProps;

/**
 * @return {JSX.Element}
 */
const DatePickerInput = ({
    id,
    orientation,
    sizing,
    label,
    required,
    error,
    value,
    ...remaining
}: DatePickerInputProps) => {
    return (
        <EntryWrapper
            orientation={orientation}
            sizing={sizing}
            label={label}
            htmlFor={id}
            required={required}
            error={error}>
            <DatePicker id={id} label={label} value={value} {...remaining} />
        </EntryWrapper>
    );
};

export { DatePickerInput };
