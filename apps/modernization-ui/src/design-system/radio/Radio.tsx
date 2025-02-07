import classNames from 'classnames';
import styles from './radio.module.scss'; // Ensure you have a corresponding radio.module.scss
import { Sizing } from 'components/Entry';
import { ChangeEvent } from 'react';

type Props = Omit<JSX.IntrinsicElements['input'], 'onChange' | 'checked' | 'value'> & {
    sizing?: Sizing;
    checked?: boolean;
    onChange?: (event: ChangeEvent<HTMLInputElement>) => void;
    name: string;
    label?: string;
    value?: string;
    id?: string;
};

const Radio = ({ className, name, checked = false, sizing, onChange, label, value, id, ...inputProps }: Props) => {
    return (
        <div className={classNames(styles.radio, className, styles[sizing ?? ''])}>
            <input
                id={id}
                name={name}
                type="radio"
                value={value}
                checked={checked}
                onChange={onChange}
                {...inputProps}
            />
            <label className={classNames({ [styles.disabled]: inputProps.disabled })} htmlFor={id}>
                {label}
            </label>
        </div>
    );
};

export { Radio };
