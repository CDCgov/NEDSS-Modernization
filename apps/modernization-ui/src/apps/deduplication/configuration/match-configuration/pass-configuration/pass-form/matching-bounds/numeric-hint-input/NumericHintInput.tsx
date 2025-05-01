import classNames from 'classnames';
import { InlineErrorMessage } from 'design-system/field/InlineErrorMessage';
import { Hint } from 'design-system/hint';
import { Numeric } from 'design-system/input/numeric/Numeric';
import { ReactNode } from 'react';
import styles from './numeric-hint-input.module.scss';

type Props = {
    name: string;
    label: string;
    error?: string;
    onBlur: () => void;
    onChange: () => void;
    min?: number;
    max?: number;
    step?: number;
    value?: number;
    tooltip: ReactNode;
    disabled?: boolean;
    className?: string;
};
export const NumericHintInput = ({
    name,
    label,
    value,
    error,
    tooltip,
    min,
    max,
    step,
    className,
    disabled = false,
    onBlur,
    onChange
}: Props) => {
    return (
        <div className={classNames(className, styles.boundEntry, error ? styles.hasError : '')}>
            <label htmlFor={name} aria-describedby={`${name}-info`}>
                {label}
                <Hint id={`${name}-info`}>{tooltip}</Hint>
            </label>
            <div>
                {error && <InlineErrorMessage id={`${name}-error`}>{error}</InlineErrorMessage>}
                <Numeric
                    inputMode="decimal"
                    id={name}
                    value={value}
                    disabled={disabled}
                    min={min}
                    max={max}
                    step={step}
                    onBlur={onBlur}
                    onChange={onChange}
                />
            </div>
        </div>
    );
};
