import { BasicFilterComponent, BasicFilterProps } from './BasicFilter';
import { BasicFilterConfiguration } from 'generated';
import { DatePickerRange } from 'design-system/date/range/DatePickerRange';
import { validateRequiredRule } from 'validation/entry';
import { rangeValuesMissing, validateDateRange } from '../utils/rangeValidator.ts';

// Don't need `filter` for this component, but don't want to pass it down
// eslint-disable-next-line @typescript-eslint/no-unused-vars
const DateRangeFilter: BasicFilterComponent = ({ filter, value, onChange, ...remaining }: BasicFilterProps) => {
    return (
        <DatePickerRange
            value={{ between: { from: value?.[0], to: value?.[1] } }}
            onChange={(newValue) => onChange([newValue?.between?.from, newValue?.between.to])}
            {...remaining}
        />
    );
};

const getDateRange = (filter: BasicFilterConfiguration) => {
    if (!filter.defaultValues || filter.defaultValues.length !== 2) return null;

    // bas time range filters have two items
    return [filter.defaultValues[0], filter.defaultValues[1]];
};

const dateRangeValidator = (filter: BasicFilterConfiguration, label: string) => {
    return (value?: (string | undefined)[]) => {
        // Base required check doesn't work well with ranges
        if (!value || (!value[0] && !value[1])) {
            return filter.isRequired ? validateRequiredRule(label).required.message : true;
        }

        const errorMsg = validateDateRange(value, label);

        if (errorMsg) return errorMsg;

        // valid by default
        return true;
    };
};

export { DateRangeFilter, getDateRange, dateRangeValidator };
