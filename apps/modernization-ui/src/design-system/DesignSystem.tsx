import { Selectable } from 'options';
import { CheckboxGroup } from './checkbox/CheckboxGroup';
import styles from './design-system.module.scss';

const options: Selectable[] = [
    {
        value: 'unchecked',
        name: 'Unchecked',
        label: 'Unchecked'
    },
    { value: 'checked', name: 'Checked', label: 'Checked' }
];

export const DesignSystem = () => {
    return (
        <div className={styles.designSystem}>
            <h1>DesignSystem</h1>
            <h2>Checkboxes</h2>
            <CheckboxGroup
                className={styles.customCheckboxGroupClass}
                label="Enabled checkboxes"
                options={options}
                initialSelection={[options[1].value]}
                onChange={(selected) => console.log('Checkbox change:', selected)}
            />
            <CheckboxGroup
                label="Disabled checkboxes"
                options={[
                    { value: 'disabled', name: 'Disabled unchecked', label: 'Disabled unchecked' },
                    { value: 'disabledChecked', name: 'Disabled checked', label: 'Disabled checked' }
                ]}
                initialSelection={['disabledChecked']}
                disabled={true}
            />
        </div>
    );
};
