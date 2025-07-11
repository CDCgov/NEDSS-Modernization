import { useId } from 'react';
import { Selectable } from 'options';
import { Checkbox, CheckboxProps } from './Checkbox';

type SelectableCheckboxProps = {
    selectable: Selectable;
    onChange?: (value?: Selectable) => void;
} & Omit<CheckboxProps, 'onChange'>;

const SelectableCheckbox = ({ selectable, onChange, ...remaining }: SelectableCheckboxProps) => {
    const id = useId();

    const handleChange = (checked: boolean) => {
        onChange?.(checked ? selectable : undefined);
    };

    return <Checkbox id={id} label={selectable.name} {...remaining} onChange={handleChange} />;
};

export { SelectableCheckbox };
export type { SelectableCheckboxProps };
