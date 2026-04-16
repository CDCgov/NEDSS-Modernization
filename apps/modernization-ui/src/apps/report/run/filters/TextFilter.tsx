import { TextInputField } from 'design-system/input';
import { BasicFilterComponent, BasicFilterProps } from './BasicFilter';

const TextFilter: BasicFilterComponent = ({ filter, ...remaining }: BasicFilterProps) => {
    return <TextInputField {...remaining} />;
};

export { TextFilter };
