import { NumericInput } from 'design-system/input';
import { BasicFilterComponent, BasicFilterProps } from './BasicFilter';
import { BasicFilterConfiguration } from 'generated';

// Don't need `filter` for this component, but don't want to pass it down
// eslint-disable-next-line @typescript-eslint/no-unused-vars
const NumericFilter: BasicFilterComponent = ({ filter, ...remaining }: BasicFilterProps) => {
    return <NumericInput {...remaining} />;
};

const getNumericValue = (filter: BasicFilterConfiguration) => {
    if (!filter.defaultValues || filter.defaultValues.length === 0) return null;

    // bas days filters only ever have one default and it needs no interpretation
    return filter.defaultValues[0];
};

const numericValidator = (_filter: BasicFilterConfiguration, label: string) => {
    return (value?: number | null) => {
        if (value === undefined || value === null) return true;

        const min = 0;
        const max = 999;

        if (value < min) {
            return `${label} must be at least ${min}.`;
        }

        if (value > max) {
            return `${label} must not be greater than ${max}.`;
        }

        return true;
    };
};

export { NumericFilter, getNumericValue, numericValidator };
