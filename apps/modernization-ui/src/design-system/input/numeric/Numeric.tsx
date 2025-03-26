import { ChangeEvent as ReactChangeEvent, useEffect } from 'react';
import classNames from 'classnames';
import { onlyNumericKeys, onlyDecimalKeys } from './onlyNumericKeys';
import { useNumeric } from './useNumeric';

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

    const handleChange = (event: ReactChangeEvent<HTMLInputElement>) => {
        const next = event.target.value;

        if (next === '') {
            clear();
        } else if (Number.isNaN(next)) {
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
            value={current ?? ''}
            pattern="[0-9]*"
            onKeyDown={inputMode === 'numeric' ? onlyNumericKeys : onlyDecimalKeys}
            {...props}
        />
    );
};

export { Numeric };
export type { NumericProps };
