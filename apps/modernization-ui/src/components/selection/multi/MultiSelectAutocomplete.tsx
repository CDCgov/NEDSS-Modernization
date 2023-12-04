import { FocusEventHandler, useState } from 'react';
import { MultiValue, components } from 'react-select';
import AsyncSelect from 'react-select/async';
import { EntryWrapper } from 'components/Entry';
import { Selectable } from 'options';

import './MultiSelectInput.scss';

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

type Complete = (criteria: string) => Promise<Selectable[]>;

type MultiSelectInputProps = {
    label?: string;
    id?: string;
    name?: string;
    placeholder?: string;
    orientation?: 'horizontal' | 'vertical';
    complete?: Complete;
    options?: Selectable[];
    value?: Selectable[];
    onChange?: (value: any) => void;
    onBlur?: FocusEventHandler<HTMLInputElement> | undefined;
    required?: boolean;
    error?: string;
};

const MultiSelectAutocomplete = ({
    label,
    id,
    name,
    options,
    complete,
    onChange,
    onBlur,
    value = [],
    required,
    error,
    placeholder,
    orientation = 'vertical'
}: MultiSelectInputProps) => {
    const [selectedOptions, setSelectedOptions] = useState<Selectable[]>(value);

    const handleOnChange = (newValue: MultiValue<Selectable>) => {
        setSelectedOptions([...newValue]);
        if (onChange) {
            const values = newValue.map((item) => item.value);
            onChange(values);
        }
    };
    const Input = (props: any) => <components.Input {...props} maxLength={50} />;

    return (
        <div className={'multi-select-input'}>
            <EntryWrapper
                orientation={orientation}
                label={label ?? ''}
                htmlFor={id ?? ''}
                required={required}
                error={error}>
                <AsyncSelect
                    isMulti={true}
                    id={id}
                    name={name}
                    value={selectedOptions}
                    placeholder={placeholder}
                    classNamePrefix="multi-select"
                    hideSelectedOptions={false}
                    closeMenuOnSelect={false}
                    closeMenuOnScroll={false}
                    onChange={handleOnChange}
                    onBlur={onBlur}
                    loadOptions={complete}
                    defaultOptions={options}
                    components={{ Input, Option: CheckedOption, DropdownIndicator: USWDSDropdownIndicator }}
                />
            </EntryWrapper>
        </div>
    );
};

export { MultiSelectAutocomplete };
