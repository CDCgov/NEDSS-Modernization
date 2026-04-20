import { BasicFilterComponent, BasicFilterProps } from './BasicFilter';
import { BasicFilterConfiguration } from 'generated';
import { DatePickerRange } from 'design-system/date/range/DatePickerRange';

// Don't need `filter` for this component, but don't want to pass it down
// eslint-disable-next-line @typescript-eslint/no-unused-vars
const DateRangeFilter: BasicFilterComponent = ({ filter, value, onChange, ...remaining }: BasicFilterProps) => {
    // TODO: validation isn't quite right for this component yet
    return (
        <DatePickerRange
            value={{ between: { from: value?.[0], to: value?.[1] } }}
            onChange={(newValue) => onChange([newValue?.between?.from, newValue?.between.to])}
            {...remaining}
        />
    );
};

const getDateRange = (filter: BasicFilterConfiguration) => {
    if (!filter.defaultValue || filter.defaultValue.length !== 2) return null;

    // bas time range filters have two items
    return [filter.defaultValue[0], filter.defaultValue[1]];
};

export { DateRangeFilter, getDateRange };
