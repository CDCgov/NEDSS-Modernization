import { ChangeEvent } from 'react';
import classNames from 'classnames';
import { findByValue, Selectable } from 'options';
import { Sizing } from 'design-system/field';

type SelectProps = {
    id: string;
    className?: string;
    options: Selectable[];
    value?: Selectable | null;
    onChange?: (value: Selectable | null) => void;
    placeholder?: string;
    sizing?: Sizing;
    useLabel?: boolean | undefined;
} & Omit<JSX.IntrinsicElements['select'], 'defaultValue' | 'onChange' | 'value'>;

const renderOptions = (options: Selectable[], placeholder?: string, useLabel?: boolean) => (
    <>
        {placeholder && (
            <option key={-1} value="" aria-label="no value selected">
                {placeholder}
            </option>
        )}
        {options?.map((item, index) => (
            <option key={index} value={item.value} aria-label={item.label}>
                {useLabel ? item.label : item.name}
            </option>
        ))}
    </>
);

const Select = ({
    id,
    className,
    options,
    value,
    onChange,
    placeholder = '- Select -',
    useLabel = false,
    ...inputProps
}: SelectProps) => {
    const handleChange = (event: ChangeEvent<HTMLSelectElement>) => {
        const selected = findByValue(options)(event.target.value) ?? null;
        onChange?.(selected);
    };

    return (
        <select
            id={id}
            className={classNames('usa-select', className)}
            name={inputProps.name ?? id}
            value={value?.value ?? ''}
            onChange={handleChange}
            {...inputProps}
        >
            {renderOptions(options, placeholder, useLabel)}
        </select>
    );
};

export default Select;
