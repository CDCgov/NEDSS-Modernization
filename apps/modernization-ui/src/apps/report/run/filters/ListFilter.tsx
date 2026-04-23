import { BasicFilterComponent, BasicFilterProps } from './BasicFilter';
import { BasicFilterConfiguration } from 'generated';
import { MultiSelect } from 'design-system/select';
import { useCountyOptions, useStateOptions } from 'options/location';
import { SelectInput } from 'components/FormInputs/SelectInput';
import { useEffect } from 'react';
import { useRegionOptions } from 'options/location/region/useRegionOptions';
import { useCurrentState } from './useCurrentState';
import { Selectable } from 'options';

export const STATE_FILTER_CODE = 'J_S01';
export const COUNTY_FILTER_CODE = 'J_C01';
export const REGION_FILTER_CODE = 'J_R01';

const ListFilter: BasicFilterComponent = ({ filter, value, id, onChange, ...remaining }: BasicFilterProps) => {
    const options = OPTIONS_HOOK_MAP[filter?.filterType?.code ?? '']?.();

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

export { ListFilter, getValueList };
