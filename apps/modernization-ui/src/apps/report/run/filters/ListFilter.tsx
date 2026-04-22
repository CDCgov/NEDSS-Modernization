import { BasicFilterComponent, BasicFilterProps } from './BasicFilter';
import { BasicFilterConfiguration } from 'generated';
import { cachedSelectableResolver, useSelectableOptions } from 'options';
import Select from 'design-system/select/single/Select';
import { MultiSelect } from 'design-system/select';
import { SelectableOptionsSettings } from 'options/useSelectableOptions';
import { useCountyOptions, useStateOptions } from 'options/location';

// type Resolver = SelectableOptionsSettings<undefined>['resolver'];

// const OPTIONS_HOOK_MAP: Record<string, Resolver> = {
//     J_C01: '',
//     J_S01: useCountyOptions,
//     J_R01: '',
// };

// Don't need `filter` for this component, but don't want to pass it down
// eslint-disable-next-line @typescript-eslint/no-unused-vars
const ListFilter: BasicFilterComponent = ({ filter, ...remaining }: BasicFilterProps) => {
    const SelectComponent = filter.maxValueCount == 1 ? Select : MultiSelect;
    const options = useStateOptions()

    return <SelectComponent options={options} {...remaining} />;
};

const getValueText = (filter: BasicFilterConfiguration) => {
    if (!filter.defaultValue || filter.defaultValue.length === 0) return null;

    // if single select, just the only value, otherwise, all values
    return filter.maxValueCount == 1 ? filter.defaultValue[0] : filter.defaultValue;
};

export { ListFilter, getValueText };
