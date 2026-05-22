import { NumericInput } from 'design-system/input';
import { BasicFilterComponent, BasicFilterProps } from './BasicFilter';
import { BasicFilterConfiguration } from 'generated';

const MIN_VALUE = 0;
const MAX_VALUE = 999;

// eslint-disable-next-line @typescript-eslint/no-unused-vars
const NumericFilter: BasicFilterComponent = ({ filter, ...remaining }: BasicFilterProps) => {
    return <NumericInput min={MIN_VALUE} max={MAX_VALUE} {...remaining} />;
};

const getNumericValue = (filter: BasicFilterConfiguration) => {
    if (!filter.defaultValues || filter.defaultValues.length === 0) return null;

    // bas days filters only ever have one default and it needs no interpretation
    const rawValue = filter.defaultValues[0];

    return rawValue !== undefined && rawValue !== null ? Number(rawValue) : null;
};

const numericValidator = (_filter: BasicFilterConfiguration, label: string) => {
    return (value?: number | null) => {
        if (value === undefined || value === null) return true;

        if (value < MIN_VALUE) {
            return `${label} must be at least ${MIN_VALUE}.`;
        }

        if (value > MAX_VALUE) {
            return `${label} must not be greater than ${MAX_VALUE}.`;
        }

        return true;
    };
};

export { NumericFilter, getNumericValue, numericValidator };
