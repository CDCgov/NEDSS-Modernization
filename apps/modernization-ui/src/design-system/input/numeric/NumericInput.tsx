import { EntryWrapper, Orientation, Sizing } from 'components/Entry';

import { Numeric, NumericProps } from './Numeric';
import { NumberRangeField, NumberRangeFieldProps } from './range/NumberRangeField.tsx';

type BaseNumericInputProps = {
    id: string;
    label: string;
    orientation?: Orientation;
    sizing?: Sizing;
    error?: string;
    required?: boolean;
    helperText?: string;
} & Omit<NumericProps, 'value' | 'onChange'>;

type RangeInputProps = BaseNumericInputProps & Pick<NumberRangeFieldProps, 'value' | 'onChange'>;

type SingleInputProps = BaseNumericInputProps & Pick<NumericProps, 'value' | 'onChange'>;

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
}: SingleInputProps) => {
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

const NumericRangeInput = ({
    id,
    label,
    orientation,
    sizing,
    error,
    required,
    helperText,
    ...remaining
}: Omit<RangeInputProps, 'placeholder'>) => {
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
            <NumberRangeField id={id} sizing={sizing} required={required} {...remaining} />
        </EntryWrapper>
    );
};

export { NumericInput, NumericRangeInput };
