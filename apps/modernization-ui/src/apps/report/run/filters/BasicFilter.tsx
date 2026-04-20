import { Field } from 'design-system/field';
import { BasicFilterConfiguration, ReportColumn } from 'generated';
import { ReactNode, useId } from 'react';
import { ReportExecuteForm } from '../ReportRunPage';
import { Control, Controller, ControllerRenderProps } from 'react-hook-form';
import { validateRequiredRule } from 'validation/entry';
import { TextFilter, getValueText } from './TextFilter';
import { DateRangeFilter, getDateRange } from './DateRangeFilter';

export type BasicFilterProps = {
    filter: BasicFilterConfiguration;
    id: string;
} & Omit<Parameters<typeof Field>[0], 'htmlFor' | 'children'> & Omit<ControllerRenderProps, 'ref'>;

export type BasicFilterComponent = (props: BasicFilterProps) => ReactNode;

const FILTER_TYPE_MAP: Record<
    string,
    { FilterComponent: BasicFilterComponent; getDefaultValue: (filter: BasicFilterConfiguration) => any }
> = {
    BAS_TXT: { FilterComponent: TextFilter, getDefaultValue: getValueText },
    BAS_TIM_RANGE: { FilterComponent: DateRangeFilter, getDefaultValue: getDateRange },
};

const TEMP_DEFAULT_FILTER = {
    FilterComponent: ({ filter, id, ...remaining }: BasicFilterProps) => (
        <Field htmlFor={id} {...remaining}>
            <p>{JSON.stringify(filter)}</p>
        </Field>
    ),
    getDefaultValue: (_filter: BasicFilterConfiguration) => null,
};

const BasicFilter = ({
    filter,
    columns,
    formControl,
    ...remaining
}: {
    filter: BasicFilterConfiguration;
    columns: ReportColumn[];
    formControl: Control<ReportExecuteForm>;
}) => {
    const id = useId();
    const column = columns.find((c) => c.id === filter.reportColumnUid);
    const filterDesc = filter.filterType.filterName;
    // empty string not possible in practice, but appeases typescript
    const label = column?.columnTitle ?? column?.columnName ?? filterDesc ?? '';
    const helperText = label === filterDesc ? undefined : filterDesc;

    // Get the actual input handler for this filter type
    const { FilterComponent, getDefaultValue } =
        FILTER_TYPE_MAP[filter.filterType.filterType || ''] ?? TEMP_DEFAULT_FILTER;

    // Don't validate required-ness for uninmplemented filtrs
    const hasFilter = !!FILTER_TYPE_MAP[filter.filterType.filterType || ''];
    const isRequiredValidation = hasFilter && filter.isRequired;

    return (
        <Controller
            control={formControl}
            name={`basicFilter.${filter.reportFilterUid}`}
            rules={isRequiredValidation ? validateRequiredRule(label) : undefined}
            defaultValue={getDefaultValue(filter)}
            // ignoring the ref as it does not pass down well and isn't critical
            // eslint-disable-next-line @typescript-eslint/no-unused-vars
            render={({ field: { ref, ...fieldValues }, fieldState: { error } }) => (
                <FilterComponent
                    id={id}
                    orientation="horizontal"
                    sizing="medium"
                    required={filter.isRequired}
                    filter={filter}
                    label={label}
                    helperText={helperText}
                    error={error?.message}
                    {...remaining}
                    {...fieldValues}
                />
            )}
        />
    );
};

export { BasicFilter };
