import { BasicFilterComponent, BasicFilterProps } from './BasicFilter';
import { BasicFilterConfiguration } from 'generated';
import { DatePickerRange } from 'design-system/date/range/DatePickerRange';
import { validateRequiredRule } from 'validation/entry';

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

const dateRangeValidator = (filter: BasicFilterConfiguration, label: string) => {
    return (value?: (string | undefined)[]) => {
        // Base required check doesn't work well with ranges
        if (!value || (!value[0] && !value[1])) {
            return filter.isRequired ? validateRequiredRule(label).required.message : true;
        }

        if ((!!value[0] && !value[1]) || (!value[0] && !!value[1])) return 'Both From and To dates must be populated';

        const fromDate = new Date(value[0]!); // can't be undefined because of above checks
        const toDate = new Date(value[1]!); // can't be undefined because of above checks
        for (const { date, ind, str } of [
            { date: fromDate, ind: 0, str: 'From' },
            { date: toDate, ind: 1, str: 'To' },
        ]) {
            if (date.toString() === 'Invalid Date') {
                return `${str} date of "${value[ind]}" is not valid mm/dd/yyyy formatted date`;
            }
        }

        if (fromDate > toDate) return 'From date must be before To date';

        // valid by default
        return true;
    };
};

export { DateRangeFilter, getDateRange, dateRangeValidator };
