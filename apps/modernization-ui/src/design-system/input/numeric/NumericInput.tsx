import { ChangeEvent as ReactChangeEvent, useEffect, useMemo, useState } from 'react';
import classNames from 'classnames';
import { EntryWrapper, Orientation, Sizing } from 'components/Entry';
import { NumericInputField } from './NumericInputField';

type NumericOnChange = (value?: number) => void;

const asDisplay = (value?: string | number | null) => (value === undefined ? '' : `${value}`);

type NumericInputProps = {
    id: string;
    label: string;
    inputMode?: 'decimal' | 'numeric';
    value?: number;
    onChange?: NumericOnChange;
    orientation?: Orientation;
    sizing?: Sizing;
    error?: string;
    required?: boolean;
} & Omit<JSX.IntrinsicElements['input'], 'defaultValue' | 'onChange' | 'value' | 'type' | 'inputMode'>;

const NumericInput = ({
    id,
    name,
    label,
    inputMode = 'numeric',
    value,
    onChange,
    orientation,
    sizing,
    error,
    required,
    className,
    placeholder = 'No Data'
}: NumericInputProps) => {
    const [current, setCurrent] = useState<number | undefined>(value);

    useEffect(() => {
        onChange?.(current);
    }, [current, onChange]);

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
        <EntryWrapper
            orientation={orientation}
            sizing={sizing}
            label={label}
            htmlFor={id}
            required={required}
            error={error}>
            <NumericInputField
                inputMode={inputMode}
                className={classNames('usa-input', className)}
                name={name}
                label={label}
                value={display}
                onChange={handleChange}
                required={required}
                placeholder={placeholder}
            />
        </EntryWrapper>
    );
};

export { NumericInput };
