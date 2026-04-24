import { BasicFilterComponent, BasicFilterProps } from './BasicFilter';
import { BasicFilterConfiguration } from 'generated';
import { MultiSelect } from 'design-system/select';
import { useCountyOptions, useStateOptions } from 'options/location';
import { SelectInput } from 'components/FormInputs/SelectInput';
import { useEffect } from 'react';
import { useRegionOptions } from 'options/location/region/useRegionOptions';
import { useCurrentState } from './useCurrentState';
import { Selectable } from 'options';
import { validateRequiredRule } from 'validation/entry';

export const STATE_FILTER_CODE = 'J_S01';
export const COUNTY_FILTER_CODE = 'J_C01';
export const REGION_FILTER_CODE = 'J_R01';

const OptionSelectFilter: BasicFilterComponent = ({ filter, value, id, onChange, ...remaining }: BasicFilterProps) => {
    const filterCodeFull = filter?.filterType?.code ?? ''; // should never be empty in practice
    // ignore include nulls indicator here
    const filterCode = filterCodeFull.endsWith('_N') ? filterCodeFull.slice(0, -2) : filterCodeFull;
    const options = OPTIONS_HOOK_MAP[filterCode]?.();

    if (filter.maxValueCount === 1) {
        return <SelectInput id={id} value={value ?? undefined} onChange={onChange} options={options} {...remaining} />;
    } else {
        const multiOnChange = (values: Selectable[]) => {
            onChange(values.map((v) => v.value));
        };
        const multiValue = options.filter((selectable) => value?.includes(selectable.value)) ?? [];
        return <MultiSelect id={id} value={multiValue} onChange={multiOnChange} options={options} {...remaining} />;
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

const OPTIONS_HOOK_MAP: Record<string, (stateId?: string) => Selectable[]> = {
    [COUNTY_FILTER_CODE]: useCurrentStateCountyOptions,
    [STATE_FILTER_CODE]: useStateOptions,
    [REGION_FILTER_CODE]: useRegionOptions,
};

const getValueList = (filter: BasicFilterConfiguration) => {
    if (!filter.defaultValue || filter.defaultValue.length === 0) return null;

    // if single select, just the only value, otherwise, all values
    return filter.maxValueCount == 1 ? filter.defaultValue[0] : filter.defaultValue;
};

const optionSelectValidator = (filter: BasicFilterConfiguration, label: string) => {
    return (value?: (string | undefined)[] | string) => {
        if (typeof value === 'string') return true;
        // Base required check doesn't work well with lists
        if (!value || !value.length) {
            return filter.isRequired ? validateRequiredRule(label).required.message : true;
        }
    };
};

export { OptionSelectFilter, getValueList, optionSelectValidator };
