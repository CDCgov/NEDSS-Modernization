import classNames from 'classnames';
import { InlineErrorMessage } from 'design-system/field/InlineErrorMessage';
import { Hint } from 'design-system/hint/Hint';
import { Numeric } from 'design-system/input/numeric/Numeric';
import { ReactNode } from 'react';
import styles from './bound-entry.module.scss';

type Props = {
    name: string;
    label: string;
    error?: string;
    onBlur: () => void;
    onChange: () => void;
    value?: number;
    tooltip: ReactNode;
    disabled?: boolean;
};
export const BoundEntry = ({ name, label, value, error, tooltip, disabled = false, onBlur, onChange }: Props) => {
    return (
        <div className={classNames(styles.boundEntry, error ? styles.hasError : '')}>
            <label htmlFor={name}>
                {label}
                <Hint>{tooltip}</Hint>
            </label>
            <div>
                {error && <InlineErrorMessage id={`${name}-error`}>{error}</InlineErrorMessage>}
                <Numeric
                    inputMode="decimal"
                    id={name}
                    value={value}
                    disabled={disabled}
                    onBlur={onBlur}
                    onChange={onChange}
                />
            </div>
        </div>
    );
};
