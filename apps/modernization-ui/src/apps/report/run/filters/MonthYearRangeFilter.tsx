import { BasicFilterComponent, BasicFilterProps } from './BasicFilter';
import { BasicFilterConfiguration } from 'generated';
import { MonthYearPickerRange } from 'design-system/date/range/MonthYearPickerRange';
import { validateRequiredRule } from 'validation/entry';

const YEARS_BACK = 100;
const getThisYear = () => new Date().getFullYear();

// Don't need `filter` for this component, but don't want to pass it down
// eslint-disable-next-line @typescript-eslint/no-unused-vars
const MonthYearRangeFilter: BasicFilterComponent = ({ filter, value, onChange, ...remaining }: BasicFilterProps) => {
    const thisYear = getThisYear();
    return (
        <MonthYearPickerRange
            value={{ between: { from: value?.[0], to: value?.[1] } }}
            startYear={thisYear - YEARS_BACK}
            endYear={thisYear}
            onChange={(newValue) => onChange([newValue?.between?.from, newValue?.between.to])}
            {...remaining}
        />
    );
};

const getMonthYearRange = (filter: BasicFilterConfiguration) => {
    if (!filter.defaultValue || filter.defaultValue.length !== 2) return null;

    // bas time range filters have two items
    return [filter.defaultValue[0], filter.defaultValue[1]];
};

const toDateParts = (dtStr: string) => dtStr.split('/').map((v) => parseInt(v));

const monthYearRangeValidator = (filter: BasicFilterConfiguration, label: string) => {
    return (value?: (string | undefined)[]) => {
        // Base required check doesn't work well with ranges
        if (!value || (!value[0] && !value[1])) {
            return filter.isRequired ? validateRequiredRule(label).required.message : true;
        }

        if ((!!value[0] && !value[1]) || (!value[0] && !!value[1])) return 'Both From and To dates must be populated';

        // value parts can't be undefined at this point, but typescript doesn't believe us
        const fromParts = toDateParts(value[0]!);
        const toParts = toDateParts(value[1]!);
        for (const { parts, str } of [
            { parts: fromParts, str: 'From' },
            { parts: toParts, str: 'To' },
        ]) {
            const [month, year] = parts;
            if (!month || !year) return `${str} field must have both Month and Year populated`;
        }

        const fromDate = new Date(fromParts[1], fromParts[0]);
        const toDate = new Date(toParts[1], toParts[0]);
        if (fromDate > toDate) return 'From date must be before To date';

        // valid by default
        return true;
    };
};

export { MonthYearRangeFilter, getMonthYearRange, monthYearRangeValidator };
