import { BasicFilterComponent, BasicFilterProps } from './BasicFilter';
import { BasicFilterConfiguration } from 'generated';
import { cachedSelectableResolver, Selectable, useSelectableOptions } from 'options';
import Select from 'design-system/select/single/Select';
import { MultiSelect } from 'design-system/select';
import { SelectableOptionsSettings } from 'options/useSelectableOptions';
import { useCountyOptions, useStateOptions } from 'options/location';
import { SelectInput } from 'components/FormInputs/SelectInput';
import { useFormContext, useWatch } from 'react-hook-form';
import { useEffect } from 'react';

export const STATE_FILTER_CODE = 'J_S01';
export const COUNTY_FILTER_CODE = 'J_C01';
export const REGION_FILTER_CODE = 'J_R01';

const ListFilter: BasicFilterComponent = ({ filter, stateFilterId, value, ...remaining }: BasicFilterProps) => {
    // const state = useWatch({name: `basicFilters.${stateFilterId}`})
    const SelectComponent = filter.maxValueCount == 1 ? SelectInput : MultiSelect;
    const options = HOOK_MAP[filter.filterType.code ?? '']?.(stateFilterId);

    return <SelectComponent options={options} value={value ?? undefined} {...remaining} />;
};

const useStateFilter = () => {
    return useStateOptions();
};

const useCountyFilter = (stateFilterId?: string) => {
    const state = useWatch({ name: `basicFilter.${stateFilterId}` });
    const { options, load } = useCountyOptions();

    useEffect(() => {
        load(state);
    }, [state]);

    return options;
};

const HOOK_MAP: Record<string, (stateId?: string) => Selectable[]> = {
    [COUNTY_FILTER_CODE]: useCountyFilter,
    [STATE_FILTER_CODE]: useStateFilter,
    [REGION_FILTER_CODE]: '',
};

const getValueList = (filter: BasicFilterConfiguration) => {
    if (!filter.defaultValue || filter.defaultValue.length === 0) return null;

    // if single select, just the only value, otherwise, all values
    return filter.maxValueCount == 1 ? filter.defaultValue[0] : filter.defaultValue;
};

export { ListFilter, getValueList };
