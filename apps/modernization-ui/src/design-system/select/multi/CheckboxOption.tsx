import { OptionProps, components } from 'react-select';
import { Checkbox } from 'design-system/checkbox';
import { Selectable } from 'options';

import styles from './checkbox-option.module.scss';

const CheckboxOption = (props: OptionProps<Selectable, true>) => (
    <components.Option {...props} className={styles.option}>
        <Checkbox label={props.label} selected={props.isSelected} />
    </components.Option>
);

export { CheckboxOption };
