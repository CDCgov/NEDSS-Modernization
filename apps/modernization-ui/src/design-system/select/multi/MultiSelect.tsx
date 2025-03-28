import { FocusEventHandler, useState } from 'react';
import Select, { MultiValue } from 'react-select';
import { Field, Orientation, Sizing } from 'design-system/field';
import { Selectable, asValue as asSelectableValue } from 'options';
import classNames from 'classnames';

import { styles, theme } from 'design-system/select/multi';
import { CheckboxOption } from './CheckboxOption';
import { DropdownIndicator } from './DropdownIndicator';

type MultiSelectProps = {
    id: string;
    name: string;
    label: string;
    placeholder?: string;
    disabled?: boolean;
    options: Selectable[];
    value?: Selectable[];
    onBlur?: FocusEventHandler<HTMLInputElement>;
    onChange?: (value: Selectable[]) => void;
    orientation?: Orientation;
    sizing?: Sizing;
    error?: string;
    required?: boolean;
    asValue?: (selectable: Selectable) => string;
    asDisplay?: (selectable: Selectable) => string;
};

export const MultiSelect = ({
    id,
    name,
    label,
    options,
    value = [],
    onChange,
    onBlur,
    orientation,
    sizing,
    error,
    required,
    placeholder = '- Select -',
    disabled = false,
    asValue = asSelectableValue,
    asDisplay = (selectable: Selectable) => selectable.name
}: MultiSelectProps) => {
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
        <Field orientation={orientation} sizing={sizing} label={label} htmlFor={id} required={required} error={error}>
            <Select<Selectable, true>
                theme={theme}
                styles={styles}
                isMulti
                inputId={id}
                name={name}
                options={options}
                value={value}
                onChange={handleOnChange}
                onBlur={onBlur}
                placeholder={placeholder}
                isDisabled={disabled}
                className={classNames(
                    'multi-select',
                    { 'multi-select__medium': sizing === 'medium' },
                    { 'multi-select__small': sizing === 'small' }
                )}
                classNamePrefix="multi-select"
                hideSelectedOptions={false}
                closeMenuOnSelect={false}
                closeMenuOnScroll={false}
                inputValue={searchText}
                onInputChange={handleInputChange}
                getOptionValue={asValue}
                getOptionLabel={asDisplay}
                components={{ Option: CheckboxOption, DropdownIndicator: DropdownIndicator }}
            />
        </Field>
    );
};

export type { MultiSelectProps };
