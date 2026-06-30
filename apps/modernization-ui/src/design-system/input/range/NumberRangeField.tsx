import React from 'react';
import { Field, Orientation, Sizing } from 'design-system/field';

import styles from './number-range-field.module.scss';
import classNames from 'classnames';
import { Numeric } from '../numeric/Numeric.tsx';

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
    required?: boolean;
    orientation?: Orientation;
    helperText?: string;
    error?: string;
};

const parseValue = (value: number | undefined | null) => {
    if (value === undefined || value === null) return '';
    return value;
};

const NumberRangeField = ({
    id,
    value,
    onChange,
    onBlur,
    label,
    required,
    sizing,
    orientation,
    helperText,
    error,
}: NumberRangeFieldProps) => {
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
                <Field
                    orientation={orientation}
                    sizing={sizing}
                    label={'From'}
                    htmlFor={`${id}-from`}
                    required={required}
                    error={error}
                    helperText={helperText}
                >
                    <Numeric
                        id={`${id}-from`}
                        value={parseValue(value?.between?.from)}
                        onChange={(v) => handleFieldOnChange(v, 'from')}
                        onBlur={onBlur}
                        required={required}
                    />
                </Field>
            </div>
            <div className={classNames(styles['range-wrapper'], 'to')}>
                <Field
                    orientation={orientation}
                    sizing={sizing}
                    label={'To'}
                    htmlFor={`${id}-to`}
                    required={required}
                    error={error}
                    helperText={helperText}
                >
                    <Numeric
                        id={`${id}-to`}
                        min={parseValue(value?.between?.from)}
                        value={parseValue(value?.between?.to)}
                        onChange={(v) => handleFieldOnChange(v, 'to')}
                        onBlur={onBlur}
                        required={required}
                    />
                </Field>
            </div>
        </div>
    );
};

export { NumberRangeField };
