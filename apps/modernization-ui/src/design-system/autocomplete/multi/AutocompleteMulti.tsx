import { useState } from 'react';

import { EntryWrapper } from 'components/Entry';
import { MultiSelectProps } from 'design-system/select';
import { CheckboxOption, styles, theme } from 'design-system/select/multi';
import { asValue as asSelectableValue, Selectable } from 'options';
import { AutocompleteOptionsResolver } from 'options/autocompete';
import { MultiValue } from 'react-select';
import AsyncSelect from 'react-select/async';

const asSelectableDisplay = (selectable: Selectable) => selectable.name;

export type AutocompleteMultiProps = MultiSelectProps & {
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
    orientation,
    sizing,
    disabled = false,
    asValue = asSelectableValue,
    asDisplay = asSelectableDisplay,
    resolver,
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
        <EntryWrapper
            orientation={orientation}
            sizing={sizing}
            label={label}
            htmlFor={id}
            required={required}
            error={error}
        >
            <AsyncSelect<Selectable, true>
                theme={theme}
                styles={styles}
                isMulti={true}
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
                components={{ Option: CheckboxOption }}
                getOptionValue={asValue}
                getOptionLabel={asDisplay}
            />
        </EntryWrapper>
    );
};

export { AutocompleteMulti };
