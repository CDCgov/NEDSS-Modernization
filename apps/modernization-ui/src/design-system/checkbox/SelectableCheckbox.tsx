import classNames from 'classnames';
import { Selectable } from 'options';
import styles from './checkbox.module.scss';
import { Sizing } from 'components/Entry';

type Props = Omit<JSX.IntrinsicElements['input'], 'onChange' | 'checked' | 'value'> & {
    sizing?: Sizing;
    selectable: Selectable;
    selected?: boolean;
    onChange?: (value?: Selectable) => void;
};

const SelectableCheckbox = ({ className, selectable, selected = false, sizing, onChange, ...inputProps }: Props) => {
    const id = `${inputProps.name ? inputProps.name + '__' : ''}checkbox__${selectable.value}`;

    const handleChange = (checked: boolean) => {
        onChange?.(checked ? selectable : undefined);
    };
    return (
        <div className={classNames(styles.checkbox, className, styles[sizing ?? ''])}>
            <input
                id={id}
                type="checkbox"
                value={selectable.value}
                checked={selected}
                onChange={(e) => handleChange(e.target.checked)}
                {...inputProps}
            />
            <label className={classNames({ [styles.disabled]: inputProps.disabled })} htmlFor={id}>
                {selectable.name}
            </label>
        </div>
    );
};

export { SelectableCheckbox };
