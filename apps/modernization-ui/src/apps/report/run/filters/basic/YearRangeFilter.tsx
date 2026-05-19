import { BasicFilterComponent, BasicFilterProps } from './BasicFilter';
import { BasicFilterConfiguration } from 'generated';
import { YearPickerRange } from 'design-system/date/range/YearPickerRange';

const YEARS_BACK = 20;
const getThisYear = () => new Date().getFullYear();

// Don't need `filter` for this component, but don't want to pass it down
// eslint-disable-next-line @typescript-eslint/no-unused-vars
const YearRangeFilter: BasicFilterComponent = ({ filter, value, onChange, ...remaining }: BasicFilterProps) => {
    const thisYear = getThisYear();
    return (
        <YearPickerRange
            value={{ between: { from: value?.[0], to: value?.[1] } }}
            startYear={thisYear - YEARS_BACK}
            endYear={thisYear}
            onChange={(newValue) => onChange([newValue?.between?.from, newValue?.between.to])}
            {...remaining}
        />
    );
};

const getYearRange = (filter: BasicFilterConfiguration) => {
    const thisYear = getThisYear();
    if (!filter.defaultValues || filter.defaultValues.length !== 2) return [`${thisYear - YEARS_BACK}`, `${thisYear}`];

    // bas time range filters have two items
    return [filter.defaultValues[0], filter.defaultValues[1]];
};

export { YearRangeFilter, getYearRange };
