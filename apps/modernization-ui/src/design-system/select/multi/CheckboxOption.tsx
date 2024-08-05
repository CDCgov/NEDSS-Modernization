import { OptionProps, components } from 'react-select';
import { Checkbox } from 'design-system/checkbox';
import { Selectable } from 'options';

const CheckboxOption = (props: OptionProps<Selectable, true>) => (
    <components.Option {...props}>
        <Checkbox label={props.label} selected={props.isSelected} />
    </components.Option>
);

export { CheckboxOption };
