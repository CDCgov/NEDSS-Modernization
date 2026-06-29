import { EntryWrapper, Orientation, Sizing } from 'components/Entry';
import { Numeric, NumericProps } from './Numeric';
import { NumberBetweenCriteria, NumberRangeField } from '../range/NumberRangeField.tsx';

type BaseNumericInputProps = {
    id: string;
    label: string;
    orientation?: Orientation;
    sizing?: Sizing;
    error?: string;
    required?: boolean;
    helperText?: string;
} & NumericProps;

type RangeInputProps = BaseNumericInputProps & {
    isRange: true;
    value: NumberBetweenCriteria;
};

type SingleInputProps = BaseNumericInputProps & {
    value: number;
};

type NumericInputProps = RangeInputProps | SingleInputProps;

const NumericInput = ({
    id,
    label,
    orientation,
    sizing,
    error,
    required,
    placeholder,
    helperText,
    isRange = false,
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
            {isRange ? (
                <NumberRangeField id={id} sizing={sizing} required={required} {...remaining} />
            ) : (
                <Numeric id={id} placeholder={placeholder} {...remaining} />
            )}
        </EntryWrapper>
    );
};

export { NumericInput };
