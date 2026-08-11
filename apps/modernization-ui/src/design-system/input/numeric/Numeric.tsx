import { ChangeEvent as ReactChangeEvent } from 'react';

import classNames from 'classnames';

import { onlyDecimalKeys, onlyNumericKeys } from './onlyNumericKeys';

type NumericOnChange = (value: number | null) => void;

type NumericProps = {
    id: string;
    inputMode?: 'decimal' | 'numeric';
    value: number | null;
    onChange: NumericOnChange;
    onBlur?: () => void;
} & Omit<JSX.IntrinsicElements['input'], 'defaultValue' | 'onChange' | 'value' | 'type' | 'inputMode'>;

const Numeric = ({
    id,
    inputMode = 'numeric',
    value,
    onChange,
    onBlur,
    className,
    placeholder,
    ...props
}: NumericProps) => {
    const handleChange = (event: ReactChangeEvent<HTMLInputElement>) => {
        const next = event.target.value;

        if (next === '') {
            onChange(null);
        } else {
            const adjusted = Number(next);
            if (!Number.isNaN(adjusted)) {
                onChange(adjusted);
            }
        }
    };

    return (
        <input
            id={id}
            name={props.name ?? id}
            className={classNames('usa-input', className)}
            type="number"
            inputMode={inputMode}
            onChange={handleChange}
            onBlur={onBlur}
            placeholder={placeholder}
            value={value ?? ''}
            pattern="[0-9]*"
            onKeyDown={inputMode === 'numeric' ? onlyNumericKeys : onlyDecimalKeys}
            {...props}
        />
    );
};

export { Numeric };
export type { NumericProps };
