import { BasicFilterComponent, BasicFilterProps } from './BasicFilter';
import { BasicFilterConfiguration } from 'generated';
import { MultiSelect } from 'design-system/select';
import { useCountyOptions, useStateOptions } from 'options/location';
import { SelectInput } from 'components/FormInputs/SelectInput';
import { useEffect } from 'react';
import { useCurrentState } from './useCurrentState';
import { Selectable } from 'options';
import { validateRequiredRule } from 'validation/entry';

export const STATE_FILTER_CODE = 'J_S01';
export const COUNTY_FILTER_CODE = 'J_C01';
export const REGION_FILTER_CODE = 'J_R01';

const OptionSelectFilter: BasicFilterComponent = ({ filter, value, onChange, ...remaining }: BasicFilterProps) => {
    const filterCodeFull = filter?.filterType?.code ?? ''; // should never be empty in practice
    // ignore include nulls indicator here
    const filterCode = filterCodeFull.endsWith('_N') ? filterCodeFull.slice(0, -2) : filterCodeFull;
    const options = OPTIONS_HOOK_MAP[filterCode]?.() ?? [];

    useEffect(() => {
        // options have changed and the value is no longer in the option set
        if (value?.length > 0 && options.length > 0 && !value.every((v: string) => options.find((o) => o.value == v))) {
            onChange(undefined);
        }
    }, [options]);

    if (filter.maxValueCount === 1) {
        return (
            <SelectInput
                value={value?.[0] ?? undefined}
                onChange={(event) => onChange(event.target.value ? [event.target.value] : undefined)}
                options={options}
                {...remaining}
            />
        );
    } else {
        const multiOnChange = (values: Selectable[]) => {
            onChange(values.map((v) => v.value));
        };
        const multiValue = options.filter((selectable) => value?.includes(selectable.value)) ?? [];
        return <MultiSelect value={multiValue} onChange={multiOnChange} options={options} {...remaining} />;
    }
};

// county options depend on the currently selected state basic filter
const useCurrentStateCountyOptions = () => {
    const state = useCurrentState();
    const { options, load } = useCountyOptions();

    useEffect(() => {
        load(state);
    }, [state]);

    return options;
};

const OPTIONS_HOOK_MAP: Record<string, () => Selectable[]> = {
    [COUNTY_FILTER_CODE]: useCurrentStateCountyOptions,
    [STATE_FILTER_CODE]: useStateOptions,
    // this may be functionally dead code - needs more investigation
    [REGION_FILTER_CODE]: () => [],
};

const getValueList = (filter: BasicFilterConfiguration) => {
    if (!filter.defaultValue || filter.defaultValue.length === 0) return null;

    return filter.defaultValue;
};

const optionSelectValidator = (filter: BasicFilterConfiguration, label: string) => {
    return (value?: (string | undefined)[]) => {
        // Base required check doesn't work well with lists
        if (!value || !value.length) {
            return filter.isRequired ? validateRequiredRule(label).required.message : true;
        }

        return true;
    };
};

export { OptionSelectFilter, getValueList, optionSelectValidator };
