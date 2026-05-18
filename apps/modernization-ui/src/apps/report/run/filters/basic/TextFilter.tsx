import { TextInputField } from 'design-system/input';
import { BasicFilterComponent, BasicFilterProps } from './BasicFilter';
import { BasicFilterConfiguration } from 'generated';

// Don't need `filter` for this component, but don't want to pass it down
// eslint-disable-next-line @typescript-eslint/no-unused-vars
const TextFilter: BasicFilterComponent = ({ filter, ...remaining }: BasicFilterProps) => {
    return <TextInputField {...remaining} />;
};

const getValueText = (filter: BasicFilterConfiguration) => {
    if (!filter.defaultValue || filter.defaultValue.length === 0) return null;

    // bas text filters only ever have one default and it needs no interpretation
    return filter.defaultValue[0];
};

export { TextFilter, getValueText };
