import { ChangeEvent } from 'react';
import classNames from 'classnames';
import { findByValue, Selectable } from 'options';
import { Sizing } from 'design-system/field';

type SelectProps = {
    id: string;
    className?: string;
    options: Selectable[];
    value?: Selectable | null;
    onChange?: (value?: Selectable) => void;
    placeholder?: string;
    sizing?: Sizing;
} & Omit<JSX.IntrinsicElements['select'], 'defaultValue' | 'onChange' | 'value'>;

const renderOptions = (options: Selectable[], placeholder?: string) => (
    <>
        {placeholder && (
            <option key={-1} value="">
                {placeholder}
            </option>
        )}
        {options?.map((item, index) => (
            <option key={index} value={item.value}>
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
    const find = findByValue(options);

    const handleChange = (event: ChangeEvent<HTMLSelectElement>) => {
        if (onChange) {
            const selected = find(event.target.value);
            onChange(selected);
        }
    };

    return (
        <select
            key={value?.value ?? ''}
            id={id}
            className={classNames('usa-select', className)}
            name={inputProps.name ?? id}
            value={value?.value}
            onChange={handleChange}
            {...inputProps}>
            {renderOptions(options, placeholder)}
        </select>
    );
};

export default Select;
