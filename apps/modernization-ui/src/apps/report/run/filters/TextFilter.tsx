import { TextInputField } from 'design-system/input';
import { BasicFilterComponent, BasicFilterProps } from './BasicFilter';
import { FilterConfiguration } from 'generated';

const TextFilter: BasicFilterComponent = ({ filter, ...remaining }: BasicFilterProps) => {
    return <TextInputField {...remaining} />;
};

const getValueText = (filter: FilterConfiguration) => {
    if (filter.filterDefaultValues.length === 0) return null;

    // bas text filters only ever have one default and it needs no interpretation
    return filter.filterDefaultValues[0].valueTxt
}

export { TextFilter, getValueText };
