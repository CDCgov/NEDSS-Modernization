import { FocusEventHandler, useState } from 'react';
import { MultiValue, components } from 'react-select';
import AsyncSelect from 'react-select/async';
import { EntryWrapper, Orientation } from 'components/Entry';
import { Selectable, asValue as asSelectableValue } from 'options';

import { AutocompleteOptionsResolver } from 'options/autocompete';

const CheckedOption = (props: any) => {
    return (
        <div>
            <components.Option {...props}>
                <input type="checkbox" checked={props.isSelected} readOnly /> <label>{props.label}</label>
            </components.Option>
        </div>
    );
};

const asSelectableDisplay = (selectable: Selectable) => selectable.name;

type AutocompleteMultiProps = {
    id: string;
    name: string;
    label: string;
    placeholder?: string;
    disabled?: boolean;
    options?: Selectable[];
    value?: Selectable[];
    onBlur?: FocusEventHandler<HTMLInputElement>;
    onChange?: (value: Selectable[]) => void;
    orientation?: Orientation;
    error?: string;
    required?: boolean;
    asValue?: (selectable: Selectable) => string;
    asDisplay?: (selectable: Selectable) => string;
    resolver: AutocompleteOptionsResolver;
};

const AutocompleteMulti = ({
    label,
    id,
    name,
    options,
    onChange,
    onBlur,
    value = [],
    required,
    error,
    placeholder = '- Select -',
    orientation = 'vertical',
    disabled = false,
    asValue = asSelectableValue,
    asDisplay = asSelectableDisplay,
    resolver
}: AutocompleteMultiProps) => {
    const [searchText, setSearchText] = useState('');
    const [selected, setSelected] = useState<Selectable[]>(value);

    const handleOnChange = (newValue: MultiValue<Selectable>) => {
        const nextSelection = newValue as Selectable[];
        setSelected(nextSelection);

        if (onChange) {
            onChange(nextSelection);
        }
    };

    const handleInputChange = (searchText: string, action: { action: string }) => {
        if (action.action !== 'input-blur' && action.action !== 'set-value') {
            setSearchText(searchText);
        }
    };

    return (
        <div className={'multi-select-input'}>
            <EntryWrapper orientation={orientation} label={label} htmlFor={id} required={required} error={error}>
                <AsyncSelect<Selectable, true>
                    isMulti
                    id={id}
                    name={name}
                    value={selected}
                    placeholder={placeholder}
                    isDisabled={disabled}
                    classNamePrefix="multi-select"
                    hideSelectedOptions={false}
                    closeMenuOnSelect={false}
                    closeMenuOnScroll={false}
                    inputValue={searchText}
                    onInputChange={handleInputChange}
                    onChange={handleOnChange}
                    onBlur={onBlur}
                    loadOptions={(criteria: string) => resolver(criteria)}
                    defaultOptions={options}
                    components={{ Option: CheckedOption }}
                    getOptionValue={asValue}
                    getOptionLabel={asDisplay}
                />
            </EntryWrapper>
        </div>
    );
};

export { AutocompleteMulti };
