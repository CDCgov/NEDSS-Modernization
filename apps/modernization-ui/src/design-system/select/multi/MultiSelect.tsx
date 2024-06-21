import React, { useState } from 'react';
import Select, { MultiValue, components } from 'react-select';
import { EntryWrapper, Orientation } from 'components/Entry';
import { Selectable } from 'options';

type MultiSelectProps = {
    id: string;
    name: string;
    label: string;
    options: Selectable[];
    value?: Selectable[];
    onChange?: (value: Selectable[]) => void;
    orientation?: Orientation;
    error?: string;
    required?: boolean;
    placeholder?: string;
    disabled?: boolean;
};

const CheckedOption = (props: any) => {
    return (
        <>
            <components.Option {...props}>
                <input type="checkbox" checked={props.isSelected} readOnly /> <label>{props.label}</label>
            </components.Option>
        </>
    );
};

export const MultiSelect: React.FC<MultiSelectProps> = ({
    id,
    name,
    label,
    options,
    value = [],
    onChange,
    orientation = 'vertical',
    error,
    required,
    placeholder = '- Select -',
    disabled = false
}) => {
    const [searchText, setSearchText] = useState('');

    const handleOnChange = (newValue: MultiValue<Selectable>) => {
        if (onChange) {
            onChange(newValue as Selectable[]);
        }
    };

    const handleInputChange = (searchText: string, action: { action: string }) => {
        if (action.action !== 'input-blur' && action.action !== 'set-value') {
            setSearchText(searchText);
        }
    };
    return (
        <div>
            <EntryWrapper orientation={orientation} label={label} htmlFor={id} required={required} error={error}>
                <Select<Selectable, true>
                    isMulti
                    id={id}
                    name={name}
                    options={options}
                    value={value}
                    onChange={handleOnChange}
                    placeholder={placeholder}
                    isDisabled={disabled}
                    classNamePrefix="multi-select"
                    hideSelectedOptions={false}
                    closeMenuOnSelect={false}
                    closeMenuOnScroll={false}
                    inputValue={searchText}
                    onInputChange={handleInputChange}
                    getOptionLabel={(option) => option.label}
                    getOptionValue={(option) => option.value}
                    components={{ Option: CheckedOption }}
                />
            </EntryWrapper>
        </div>
    );
};
