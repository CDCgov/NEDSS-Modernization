import { FocusEventHandler, useState } from 'react';
import classNames from 'classnames';
import Select, { MultiValue } from 'react-select';
import { Selectable, asValue as asSelectableValue } from 'options';
import { Field, FieldProps } from 'design-system/field';
import { styles, theme } from './design';
import { CheckboxOption } from './CheckboxOption';
import { DropdownIndicator } from './DropdownIndicator';

type MultiSelectProps = {
    id: string;
    name: string;
    placeholder?: string;
    disabled?: boolean;
    options: Selectable[];
    value?: Selectable[];
    onBlur?: FocusEventHandler<HTMLInputElement>;
    onChange?: (value: Selectable[]) => void;
    asValue?: (selectable: Selectable) => string;
    asDisplay?: (selectable: Selectable) => string;
} & FieldProps;

export const MultiSelect = ({
    id,
    name,
    options,
    value = [],
    onChange,
    onBlur,
    placeholder = '- Select -',
    disabled = false,
    sizing,
    asValue = asSelectableValue,
    asDisplay = (selectable: Selectable) => selectable.name,
    ...remaining
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
        <Field htmlFor={id} {...remaining} sizing={sizing}>
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
