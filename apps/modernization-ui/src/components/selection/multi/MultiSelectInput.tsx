import ReactSelect, { MultiValue, components } from 'react-select';
import { FocusEventHandler, useEffect, useMemo, useState } from 'react';
import './MultiSelectInput.scss';
import { mapNonNull } from 'utils';
import { Label, ErrorMessage } from '@trussworks/react-uswds';

const CheckedOption = (props: any) => {
    return (
        <div>
            <components.Option {...props}>
                <input type="checkbox" checked={props.isSelected} readOnly /> <label>{props.label}</label>
            </components.Option>
        </div>
    );
};

const USWDSDropdownIndicator = (props: any) => (
    // Replaces the default arrow indicator from react-select with the select indicator from USDWS
    <components.DropdownIndicator {...props}>
        <div className="multi-select select-indicator" />
    </components.DropdownIndicator>
);

const asSelectable = (selectables: Selectable[]) => (item: string) =>
    selectables.find((option) => option.value === item) || null;

type Option = { name: string; value: string };

type MultiSelectInputProps = {
    label?: string;
    id?: string;
    name?: string;
    options?: Option[];
    value?: string[];
    onChange?: (value: string[]) => void;
    onBlur?: FocusEventHandler<HTMLInputElement> | undefined;
    required?: boolean;
    error?: string;
};

type Selectable = { value: string; label: string };

export const MultiSelectInput = ({
    label,
    id,
    name,
    options = [],
    onChange,
    onBlur,
    value = [],
    required,
    error
}: MultiSelectInputProps) => {
    const selectableOptions = useMemo(
        () => options.map((item) => ({ value: item.value, label: item.name })),
        [JSON.stringify(options)]
    );

    const [selectedOptions, setSelectedOptions] = useState<Selectable[]>([]);

    useEffect(() => {
        const selected = mapNonNull(asSelectable(selectableOptions), value);
        setSelectedOptions(selected);
    }, [JSON.stringify(value), selectableOptions]);

    const handleOnChange = (newValue: MultiValue<Selectable>) => {
        console.log('CHANGE', newValue, selectableOptions);
        setSelectedOptions([...newValue]);

        if (onChange) {
            const values = newValue.map((item) => item.value);
            onChange(values);
        }
    };

    return (
        <div className={`multi-select-input ${required ? 'required' : ''}`}>
            {label && (
                <Label htmlFor={label}>
                    {label}
                    <small className="text-red">{required && ' *'}</small>
                </Label>
            )}
            <ErrorMessage id={`${error}-message`}>{error}</ErrorMessage>
            <ReactSelect
                isMulti={true}
                id={id}
                name={name}
                value={selectedOptions}
                placeholder="- Select -"
                classNamePrefix="multi-select"
                hideSelectedOptions={false}
                closeMenuOnSelect={false}
                closeMenuOnScroll={false}
                onChange={handleOnChange}
                onBlur={onBlur}
                options={selectableOptions}
                components={{ Option: CheckedOption, DropdownIndicator: USWDSDropdownIndicator }}
            />
        </div>
    );
};
