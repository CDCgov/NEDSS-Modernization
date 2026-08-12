import classNames from 'classnames';

import { Field, Orientation, Sizing } from 'design-system/field';

import { Numeric } from '../Numeric.tsx';

import styles from './number-range-field.module.scss';

type NumberRange = {
    from: number | null;
    to: number | null;
};

export type NumberBetweenCriteria = {
    between: NumberRange;
};

const initialNumberBetweenCriteria = { between: { from: null, to: null } };

export type NumberRangeFieldProps = {
    id: string;
    value: NumberBetweenCriteria;
    sizing?: Sizing;
    onChange: (value: NumberBetweenCriteria) => void;
    onBlur?: () => void;
    label?: string;
    required?: boolean;
    orientation?: Orientation;
    helperText?: string;
    error?: string;
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
    const handleFieldOnChange = (v: number | null, field: keyof NumberRange) => {
        onChange({ between: { ...value.between, [field]: v } });
    };

    return (
        <div id={id} data-testid="number-range-editor" role="group" className={styles.layout} aria-label={label}>
            <div className={classNames(styles['range-wrapper'], 'from')}>
                <Field
                    orientation={orientation}
                    sizing={sizing}
                    label="From"
                    htmlFor={`${id}-from`}
                    required={required}
                    error={error}
                    helperText={helperText}
                >
                    <Numeric
                        id={`${id}-from`}
                        value={value.between.from}
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
                    label="To"
                    htmlFor={`${id}-to`}
                    required={required}
                    error={error}
                    helperText={helperText}
                >
                    <Numeric
                        id={`${id}-to`}
                        min={value.between.from ?? undefined}
                        value={value.between.to}
                        onChange={(v) => handleFieldOnChange(v, 'to')}
                        onBlur={onBlur}
                        required={required}
                    />
                </Field>
            </div>
        </div>
    );
};

export { NumberRangeField, initialNumberBetweenCriteria };
