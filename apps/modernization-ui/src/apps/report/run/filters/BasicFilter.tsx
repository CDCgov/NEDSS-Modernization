import { Field } from 'design-system/field';
import { BasicFilterConfiguration, ReportColumn } from 'generated';
import { ReactNode, useId } from 'react';
import { ReportExecuteForm } from '../ReportRunPage';
import { Controller, ControllerRenderProps, RegisterOptions, useFormContext } from 'react-hook-form';
import { validateRequiredRule } from 'validation/entry';
import { TextFilter, getValueText } from './TextFilter';
import { DateRangeFilter, dateRangeValidator, getDateRange } from './DateRangeFilter';
import { Validator } from 'validation';
import { getValueList, ListFilter } from './ListFilter';
import { getYearRange, YearRangeFilter } from './YearRangeFilter';
import { getMonthYearRange, MonthYearRangeFilter, monthYearRangeValidator } from './MonthYearRangeFilter';

export type BasicFilterProps = {
    filter: BasicFilterConfiguration;
    stateFilterId?: string;
    id: string;
} & Omit<Parameters<typeof Field>[0], 'htmlFor' | 'children'> &
    Omit<ControllerRenderProps, 'ref'>;

export type BasicFilterComponent = (props: BasicFilterProps) => ReactNode;

const FILTER_TYPE_MAP: Record<
    string,
    {
        FilterComponent: BasicFilterComponent;
        getDefaultValue: (filter: BasicFilterConfiguration) => any;
        validationRule?: (filter: BasicFilterConfiguration, label: string) => Validator<any>;
    }
> = {
    BAS_TXT: { FilterComponent: TextFilter, getDefaultValue: getValueText },
    BAS_TIM_RANGE: {
        FilterComponent: DateRangeFilter,
        getDefaultValue: getDateRange,
        validationRule: dateRangeValidator,
    },
    BAS_TIM_RANGE_CUSTOM: {
        FilterComponent: DateRangeFilter,
        getDefaultValue: getDateRange,
        validationRule: dateRangeValidator,
    },
    BAS_TIM_RANGE_LIST: {
        FilterComponent: YearRangeFilter,
        getDefaultValue: getYearRange,
        validationRule: dateRangeValidator,
    },
    BAS_MM_YYYY_RANGE: {
        FilterComponent: MonthYearRangeFilter,
        getDefaultValue: getMonthYearRange,
        validationRule: monthYearRangeValidator,
    },
    BAS_JUR_LIST: {
        FilterComponent: ListFilter,
        getDefaultValue: getValueList,
    },
};

const TEMP_DEFAULT_FILTER = {
    FilterComponent: ({ filter, id, ...remaining }: BasicFilterProps) => (
        <Field htmlFor={id} {...remaining}>
            <p>{JSON.stringify(filter)}</p>
        </Field>
    ),
    getDefaultValue: () => null,
};

const BasicFilter = ({
    filter,
    columns,
    ...remaining
}: {
    filter: BasicFilterConfiguration;
    columns: ReportColumn[];
}) => {
    const id = useId();
    const { control } = useFormContext<ReportExecuteForm>();
    const column = columns.find((c) => c.id === filter.reportColumnUid);
    const filterDesc = filter.filterType.filterName;
    // empty string not possible in practice, but appeases typescript
    const label = column?.columnTitle ?? column?.columnName ?? filterDesc ?? '';
    const helperText = label === filterDesc ? undefined : filterDesc;

    // Get the actual input handler for this filter type
    const { FilterComponent, getDefaultValue, validationRule } =
        FILTER_TYPE_MAP[filter.filterType.filterType || ''] ?? TEMP_DEFAULT_FILTER;

    // Don't validate required-ness for uninmplemented filtrs
    const hasFilter = !!FILTER_TYPE_MAP[filter.filterType.filterType || ''];
    const isRequiredValidation = hasFilter && filter.isRequired;

    const rules: Pick<RegisterOptions, 'required' | 'validate'> = isRequiredValidation
        ? validateRequiredRule(label)
        : {};

    if (validationRule) {
        rules.validate = validationRule(filter, label);
    }

    return (
        <Controller
            control={control}
            name={`basicFilter.${filter.reportFilterUid}`}
            rules={rules}
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
