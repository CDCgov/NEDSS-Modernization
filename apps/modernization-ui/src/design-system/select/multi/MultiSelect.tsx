import { FocusEventHandler, useState } from 'react';
import classNames from 'classnames';
import Select, { ActionMeta, FilterOptionOption, MultiValue } from 'react-select';
import { Selectable, asValue as asSelectableValue } from 'options';
import { Field, FieldProps } from 'design-system/field';
import { styles, theme } from './design';
import { CheckboxOption } from './CheckboxOption';
import { DropdownIndicator } from './DropdownIndicator';

import './multi-select.scss';

type MultiSelectProps = {
    id: string;
    name: string;
    placeholder?: string;
    required?: boolean;
    disabled?: boolean;
    options: Selectable[];
    value?: Selectable[];
    onBlur?: FocusEventHandler<HTMLInputElement>;
    onChange?: (value: Selectable[]) => void;
    asValue?: (selectable: Selectable) => string;
    asDisplay?: (selectable: Selectable) => string;
} & FieldProps;

const SELECT_ALL_VALUE = '__SELECT_ALL__';

export const MultiSelect = ({
    id,
    name,
    options,
    value = [],
    onChange,
    onBlur,
    placeholder = '- Select -',
    disabled = false,
    required,
    sizing,
    asValue = asSelectableValue,
    asDisplay = (selectable: Selectable) => selectable.name,
    ...remaining
}: MultiSelectProps) => {
    const [searchText, setSearchText] = useState('');

    const availableOptions = options.filter(
        (o) => !searchText || o.name.toLowerCase().includes(searchText.toLowerCase())
    );
    const allSelected = availableOptions.every((v) => value.includes(v));
    const selectWord = allSelected ? 'Deselect' : 'Select';

    const selectAll: Selectable = {
        value: SELECT_ALL_VALUE,
        name: selectWord + (searchText ? ' search results' : ' all'),
    };

    const optionsWithSelectAll = [selectAll, ...options];

    const handleOnChange = (newValue: MultiValue<Selectable>, actionMeta: ActionMeta<Selectable>) => {
        if (actionMeta.option?.value === SELECT_ALL_VALUE) {
            if (allSelected) {
                onChange?.(newValue!.filter((v) => !availableOptions.includes(v) && v.value !== SELECT_ALL_VALUE));
            } else {
                onChange?.([
                    ...newValue.filter((v) => v.value !== SELECT_ALL_VALUE),
                    ...availableOptions.filter((o) => !newValue.includes(o)),
                ]);
            }
        } else if (onChange) {
            onChange(newValue as Selectable[]);
        }
    };

    const handleInputChange = (searchText: string, action: { action: string }) => {
        if (action.action !== 'input-blur' && action.action !== 'set-value') {
            setSearchText(searchText);
        }
    };

    return (
        <Field htmlFor={id} {...remaining} sizing={sizing} required={required}>
            <Select<Selectable, true>
                theme={theme}
                styles={styles}
                isMulti
                inputId={id}
                name={name}
                options={optionsWithSelectAll}
                value={value}
                onChange={handleOnChange}
                onBlur={onBlur}
                placeholder={placeholder}
                isDisabled={disabled}
                required={required}
                menuIsOpen={true}
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
                filterOption={(option: FilterOptionOption<Selectable>) =>
                    option.value === SELECT_ALL_VALUE
                        ? true
                        : option.label.toLowerCase().includes(searchText.toLowerCase())
                }
            />
        </Field>
    );
};

export type { MultiSelectProps };
