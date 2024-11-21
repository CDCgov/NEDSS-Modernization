import { ChangeEvent as ReactChangeEvent, useEffect, useMemo, useState } from 'react';
import classNames from 'classnames';
import { onlyNumericKeys } from './onlyNumericKeys';

const asDisplay = (value?: string | number | null) => (value === undefined ? '' : `${value}`);

type NumericOnChange = (value?: number) => void;

type NumericProps = {
    id: string;
    inputMode?: 'decimal' | 'numeric';
    value?: number;
    onChange?: NumericOnChange;
} & Omit<JSX.IntrinsicElements['input'], 'defaultValue' | 'onChange' | 'value' | 'type' | 'inputMode'>;

const Numeric = ({ id, inputMode = 'numeric', value, onChange, className, placeholder, ...props }: NumericProps) => {
    const [current, setCurrent] = useState<number | undefined>(value);

    useEffect(() => {
        onChange?.(current);
    }, [current]);

    const display = useMemo(() => asDisplay(current), [current]);

    const handleChange = (event: ReactChangeEvent<HTMLInputElement>) => {
        const next = event.target.value;

        if (next === '') {
            setCurrent(undefined);
        } else if (Number.isNaN(next)) {
            event.preventDefault();
        } else {
            const adjusted = Number(next);
            if (!Number.isNaN(adjusted)) {
                setCurrent(adjusted);
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
            placeholder={placeholder}
            value={display}
            pattern="[0-9]*"
            onKeyDown={onlyNumericKeys}
            {...props}
        />
    );
};

export { Numeric };
export type { NumericProps };
