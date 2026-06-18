import React from 'react';
import { Sizing } from 'design-system/field';
import { NumericInput } from '../numeric';

import styles from './number-range-field.module.scss';
import classNames from 'classnames';

type NumberRange = {
    from?: number;
    to?: number;
};

export type NumberBetweenCriteria = {
    between: NumberRange;
};

export type NumberRangeFieldProps = {
    id: string;
    value?: NumberBetweenCriteria;
    sizing?: Sizing;
    onChange: (value?: NumberBetweenCriteria) => void;
    onBlur?: () => void;
    label?: string;
    required?: boolean;
};

const parseValue = (value: number | undefined | null) => {
    if (value === undefined || value === null) return undefined;
    return value;
};

const NumberRangeField = ({ id, value, sizing, onChange, onBlur, label, required }: NumberRangeFieldProps) => {
    const handleFieldOnChange = (v, type) => {
        if (type === 'to') {
            onChange({ between: { from: parseValue(value?.between.from), to: parseValue(v) } });
        }

        if (type === 'from') {
            onChange({ between: { from: parseValue(v), to: parseValue(value?.between.to) } });
        }
    };

    return (
        <div id={id} data-testid="number-range-editor" role="group" className={styles.layout} aria-label={label}>
            <div className={classNames(styles['range-wrapper'], 'from')}>
                <NumericInput
                    id={`${id}-from`}
                    label={'From'}
                    value={parseValue(value?.between?.from)}
                    onChange={(v) => handleFieldOnChange(v, 'from')}
                    onBlur={onBlur}
                    sizing={sizing}
                    required={required}
                />
            </div>
            <div className={classNames(styles['range-wrapper'], 'to')}>
                <NumericInput
                    id={`${id}-to`}
                    label={'To'}
                    min={parseValue(value?.between?.from)}
                    value={parseValue(value?.between?.to)}
                    onChange={(v) => handleFieldOnChange(v, 'to')}
                    onBlur={onBlur}
                    required={required}
                    sizing={sizing}
                />
            </div>
        </div>
    );
};

export { NumberRangeField };
