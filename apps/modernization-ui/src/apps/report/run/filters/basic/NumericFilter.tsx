import { NumericInput } from 'design-system/input';
import { BasicFilterComponent, BasicFilterProps } from './BasicFilter';
import { BasicFilterConfiguration } from 'generated';

const MIN_VALUE = 0;
const MAX_VALUE = 999;

// eslint-disable-next-line @typescript-eslint/no-unused-vars
const NumericFilter: BasicFilterComponent = ({ filter, value, onChange, ...remaining }: BasicFilterProps) => {
    const numericValue = value ? Number(value) : value;

    const numericOnChange = (v: number | null | undefined) => onChange(v || v === 0 ? v.toString() : v);

    return (
        <NumericInput min={MIN_VALUE} max={MAX_VALUE} value={numericValue} onChange={numericOnChange} {...remaining} />
    );
};

const getNumericValue = (filter: BasicFilterConfiguration) => {
    if (!filter.defaultValues || filter.defaultValues.length === 0) return null;

    // bas days filters only ever have one default and it needs no interpretation
    return filter.defaultValues[0];
};

const numericValidator = (_filter: BasicFilterConfiguration, label: string) => {
    return (value?: string | null) => {
        if (value === undefined || value === null) return true;

        const numericValue = Number(value);

        if (numericValue < MIN_VALUE) {
            return `${label} must be at least ${MIN_VALUE}.`;
        }

        if (numericValue > MAX_VALUE) {
            return `${label} must not be greater than ${MAX_VALUE}.`;
        }

        return true;
    };
};

export { NumericFilter, getNumericValue, numericValidator };
