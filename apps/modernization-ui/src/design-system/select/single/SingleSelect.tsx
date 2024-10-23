import { ChangeEvent } from 'react';
import { Select as TrussworksSelect } from '@trussworks/react-uswds';
import { EntryWrapper, Orientation, Sizing } from 'components/Entry';
import { Selectable, findByValue } from 'options';

const renderOptions = (placeholder: string, options: Selectable[]) => (
    <>
        <option value="">{placeholder}</option>
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

    //  In order for the defaultValue to be applied the component has to be re-created when it goes from null to non null.
    const Wrapped = () => (
        <TrussworksSelect
            {...inputProps}
            id={id}
            validationStatus={error ? 'error' : undefined}
            aria-describedby={error ? `${error}-message` : undefined}
            name={inputProps.name ?? id}
            defaultValue={value?.value}
            placeholder="-Select-"
            onChange={handleChange}>
            {renderOptions(placeholder, options)}
        </TrussworksSelect>
    );

    return (
        <EntryWrapper
            orientation={orientation}
            sizing={sizing}
            label={label}
            htmlFor={id}
            required={required}
            error={error}>
            {value && <Wrapped />}
            {!value && <Wrapped />}
        </EntryWrapper>
    );
};

export { SingleSelect };
