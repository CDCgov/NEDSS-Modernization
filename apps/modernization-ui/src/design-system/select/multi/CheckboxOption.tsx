import { OptionProps, components } from 'react-select';
import { DummyCheckbox } from 'design-system/checkbox/Checkbox';
import { Selectable } from 'options';

import styles from './checkbox-option.module.scss';

// React-select relies on focus being controlled and maintained in its combobox input. Because
// of this, we can't use a normal checkbox implementation as it will steal that focus and
// cause the menu list to close. The DummyCheckbox leaves the aria-semantics and control to the select
const CheckboxOption = ({ ...props }: OptionProps<Selectable, true>) => {
    return (
        <components.Option {...props} className={styles.option}>
            <DummyCheckbox label={props.label} selected={props.isSelected} />
        </components.Option>
    );
};

export { CheckboxOption };
