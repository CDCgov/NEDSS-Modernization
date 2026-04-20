import { BasicFilterComponent, BasicFilterProps } from './BasicFilter';
import { BasicFilterConfiguration } from 'generated';
import { Field } from 'design-system/field';
import { DatePicker } from 'design-system/date/picker';
import { DateRangeField } from 'design-system/date/criteria/range';

const DateRangeFilter: BasicFilterComponent = ({
    filter,
    id,
    orientation,
    sizing,
    label,
    helperText,
    required,
    error,
    value,
    onChange,
    onBlur,
    ...remaining
}: BasicFilterProps) => {
    return (
        <Field
            orientation={orientation}
            sizing={sizing}
            label={label}
            helperText={helperText}
            htmlFor={id}
            required={required}
            error={error}
        >
            <DateRangeField
                id={id}
                value={{ between: { from: value?.[0], to: value?.[1] } }}
                sizing={sizing}
                onChange={(newValue) => onChange([newValue?.between?.from, newValue?.between.to])}
                onBlur={onBlur}
                label={label}
            />
        </Field>
    );
};

const getDateRange = (filter: BasicFilterConfiguration) => {
    if (!filter.defaultValue || filter.defaultValue.length !== 2) return null;

    // bas time range filters have two items
    return [filter.defaultValue[0], filter.defaultValue[1]];
};

export { DateRangeFilter, getDateRange };
