import { NumericInput } from 'design-system/input';
import { BasicFilterComponent, BasicFilterProps } from './BasicFilter';
import { BasicFilterConfiguration } from 'generated';

// Don't need `filter` for this component, but don't want to pass it down
// eslint-disable-next-line @typescript-eslint/no-unused-vars
const NumericFilter: BasicFilterComponent = ({ filter, ...remaining }: BasicFilterProps) => {
    return <NumericInput {...remaining} />;
};

const getNumericValue = (filter: BasicFilterConfiguration) => {
    if (!filter.defaultValue || filter.defaultValue.length === 0) return null;

    // bas days filters only ever have one default and it needs no interpretation
    return filter.defaultValue[0];
};

const numericValidator = (_filter: BasicFilterConfiguration, label: string) => {
    return (value?: number | null) => {

        if (value === undefined || value === null) return true;

        if (value < 0) {
            return `${label} must not be negative`;
        }

        return true;
    };
};

export { NumericFilter, getNumericValue, numericValidator };
