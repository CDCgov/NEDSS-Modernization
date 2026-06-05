import { EntryWrapper, Orientation, Sizing } from 'components/Entry';
import { Numeric, NumericProps } from './Numeric';

type NumericInputProps = {
    id: string;
    label: string;
    orientation?: Orientation;
    sizing?: Sizing;
    error?: string;
    required?: boolean;
    helperText?: string;
} & NumericProps;

const NumericInput = ({
    id,
    label,
    orientation,
    sizing,
    error,
    required,
    placeholder,
    helperText,
    ...remaining
}: NumericInputProps) => {
    return (
        <EntryWrapper
            orientation={orientation}
            sizing={sizing}
            label={label}
            htmlFor={id}
            required={required}
            error={error}
            helperText={helperText}
        >
            <Numeric id={id} placeholder={placeholder} {...remaining} />
        </EntryWrapper>
    );
};

export { NumericInput };
