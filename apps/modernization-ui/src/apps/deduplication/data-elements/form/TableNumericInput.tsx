import { Icon, Label, Tooltip } from '@trussworks/react-uswds';
import classNames from 'classnames';
import styles from './TableNumericInput.module.scss';
import { ChangeEvent, FocusEvent } from 'react';

type Props = {
    label?: string;
    name: string;
    value?: string | number;
    error?: string;
    max?: number;
    min?: number;
    step?: number;
    disabled?: boolean;
    onChange: (event: ChangeEvent<HTMLInputElement>) => void;
    onBlur: (event: FocusEvent<HTMLInputElement>) => void;
};
export const TableNumericInput = ({ label, name, value, error, max, min, step, disabled, onChange, onBlur }: Props) => {
    return (
        <div className={styles.tableNumericInput}>
            {label && (
                <Label role="label" htmlFor={name}>
                    {label}
                </Label>
            )}
            <div className={styles.inputWithError}>
                <input
                    type="number"
                    onChange={onChange}
                    onBlur={onBlur}
                    value={value ?? ''}
                    id={name}
                    name={name}
                    max={max}
                    min={min}
                    step={step}
                    disabled={disabled}
                    className={classNames([error ? styles.errorBorder : '', styles.numericInput])}
                />
                {error && (
                    <Tooltip id={`${name}-error-tooltip`} label={error} className={styles.tooltip} position={'top'}>
                        <Icon.ErrorOutline className={styles.tooltipIcon} />
                    </Tooltip>
                )}
            </div>
        </div>
    );
};
