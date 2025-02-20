import { ChangeEvent } from 'react';
import { EntryWrapper, Orientation, Sizing } from 'components/Entry';
import { Selectable, findByValue } from 'options';
import classNames from 'classnames';

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

type Props = {
    id: string;
    label: string;
    helperText?: string;
    placeholder?: string;
    options: Selectable[];
    value?: Selectable | null;
    onChange?: (value?: Selectable) => void;
    orientation?: Orientation;
    sizing?: Sizing;
    error?: string;
    required?: boolean;
    warning?: string;
} & Omit<JSX.IntrinsicElements['select'], 'defaultValue' | 'onChange' | 'value'>;

const SingleSelect = ({
    id,
    label,
    className,
    helperText,
    options,
    value,
    onChange,
    orientation,
    sizing,
    error,
    required,
    warning,
    placeholder = '- Select -',
    ...inputProps
}: Props) => {
    const find = findByValue(options);

    const handleChange = (event: ChangeEvent<HTMLSelectElement>) => {
        if (onChange) {
            const selected = find(event.target.value);
            onChange(selected);
        }
    };

    return (
        <EntryWrapper
            orientation={orientation}
            sizing={sizing}
            label={label}
            helperText={helperText}
            htmlFor={id}
            required={required}
            error={error}
            warning={warning}>
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
        </EntryWrapper>
    );
};

export { SingleSelect };
