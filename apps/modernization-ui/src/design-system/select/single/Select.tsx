import { ChangeEvent, useCallback } from 'react';
import classNames from 'classnames';
import { findByValue, Selectable } from 'options';
import { Sizing } from 'design-system/field';

const hashed = (options: Selectable[]) => options.reduce((previous, next) => previous + next.value, '');

type SelectProps = {
    id: string;
    className?: string;
    options: Selectable[];
    value?: Selectable | null;
    onChange?: (value: Selectable | null) => void;
    placeholder?: string;
    sizing?: Sizing;
} & Omit<JSX.IntrinsicElements['select'], 'defaultValue' | 'onChange' | 'value'>;

const renderOptions = (options: Selectable[], placeholder?: string) => (
    <>
        {placeholder && (
            <option key={-1} value="" aria-label="no value selected">
                {placeholder}
            </option>
        )}
        {options?.map((item, index) => (
            <option key={index} value={item.value} aria-label={item.label}>
                {item.name}
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
    ...inputProps
}: SelectProps) => {
    const handleChange = useCallback(
        (event: ChangeEvent<HTMLSelectElement>) => {
            const selected = findByValue(options)(event.target.value) ?? null;
            onChange?.(selected);
        },
        [hashed(options), onChange]
    );

    return (
        <select
            id={id}
            className={classNames('usa-select', className)}
            name={inputProps.name ?? id}
            value={value?.value ?? ''}
            onChange={handleChange}
            {...inputProps}>
            {renderOptions(options, placeholder)}
        </select>
    );
};

export default Select;
