import { ChangeEvent } from 'react';
import { Select as TrussworksSelect } from '@trussworks/react-uswds';
import { EntryWrapper, Orientation, Sizing } from 'components/Entry';
import { Selectable, findByValue } from 'options';

const renderOptions = (options: Selectable[], placeholder?: string) => (
    <>
        {placeholder && <option value="">{placeholder}</option>}
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
    options: Selectable[];
    value?: Selectable | null;
    onChange?: (value?: Selectable) => void;
    orientation?: Orientation;
    sizing?: Sizing;
    error?: string;
    required?: boolean;
} & Omit<JSX.IntrinsicElements['select'], 'defaultValue' | 'onChange' | 'value'>;

const SingleSelect = ({
    id,
    label,
    options,
    value,
    onChange,
    orientation,
    sizing,
    error,
    required,
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
            htmlFor={id}
            required={required}
            error={error}>
            <TrussworksSelect
                key={value?.value}
                {...inputProps}
                id={id}
                validationStatus={error ? 'error' : undefined}
                name={inputProps.name ?? id}
                defaultValue={value?.value}
                placeholder="-Select-"
                onChange={handleChange}>
                {renderOptions(options, placeholder)}
            </TrussworksSelect>
        </EntryWrapper>
    );
};

export { SingleSelect };
