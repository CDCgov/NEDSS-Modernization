import { ChangeEvent as ReactChangeEvent, useEffect, useMemo } from 'react';
import classNames from 'classnames';
import { onlyNumericKeys } from './onlyNumericKeys';
import { useNumeric } from './useNumeric';

const asDisplay = (value?: string | number | null) => (value === undefined ? '' : `${value}`);

type NumericOnChange = (value?: number) => void;

type NumericProps = {
    id: string;
    inputMode?: 'decimal' | 'numeric';
    value?: number;
    onChange?: NumericOnChange;
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
    const { current, change, clear, initialize } = useNumeric(value);

    useEffect(() => {
        onChange?.(current);
    }, [current]);

    const display = useMemo(() => asDisplay(current), [current]);

    const handleChange = (event: ReactChangeEvent<HTMLInputElement>) => {
        const next = event.target.value;

        if (next === '') {
            clear();
        } else if (Number.isNaN(next)) {
            event.preventDefault();
        } else if (props.max && next > props.max) {
            event.preventDefault();
        } else {
            const adjusted = Number(next);
            if (!Number.isNaN(adjusted)) {
                change(adjusted);
            }
        }
    };

    useEffect(() => {
        initialize(value);
    }, [value]);

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
            value={display}
            pattern="[0-9]*"
            onKeyDown={onlyNumericKeys}
            {...props}
        />
    );
};

export { Numeric };
export type { NumericProps };
