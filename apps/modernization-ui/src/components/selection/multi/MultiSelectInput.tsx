import { FocusEventHandler, useEffect, useMemo, useState } from 'react';
import ReactSelect, { MultiValue } from 'react-select';
import { mapNonNull } from 'utils';
import { Selectable, asSelectable, asValue as asSelectableValue } from 'options';
import { EntryWrapper } from 'components/Entry';

import { theme, styles, CheckboxOption } from 'design-system/select/multi';

import './MultiSelectInput.scss';

const asSelected = (selectables: Selectable[]) => (item: string) =>
    selectables.find((option) => option.value === item) || null;

type Options = { name: string; value: string };

type MultiSelectInputProps = {
    label?: string;
    id?: string;
    name?: string;
    placeholder?: string;
    orientation?: 'horizontal' | 'vertical';
    options?: Options[];
    value?: string[];
    onChange?: (value: any) => void;
    onBlur?: FocusEventHandler<HTMLInputElement> | undefined;
    required?: boolean;
    disabled?: boolean;
    error?: string;
};

export const MultiSelectInput = ({
    label,
    id,
    name,
    options = [],
    onChange,
    onBlur,
    value = [],
    required,
    error,
    placeholder = '- Select -',
    orientation = 'vertical',
    disabled = false
}: MultiSelectInputProps) => {
    const selectableOptions = useMemo(
        () => options.map((item) => asSelectable(item.value, item.name)),
        [JSON.stringify(options)]
    );

    const [selectedOptions, setSelectedOptions] = useState<Selectable[]>([]);
    const [searchText, setSearchText] = useState('');

    useEffect(() => {
        const selected = mapNonNull(asSelected(selectableOptions), value);
        setSelectedOptions(selected);
    }, [JSON.stringify(value), selectableOptions]);

    const handleOnChange = (newValue: MultiValue<Selectable>) => {
        setSelectedOptions([...newValue]);
        if (onChange) {
            const values = newValue.map((item) => item.value);
            onChange(values);
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
            label={label ?? ''}
            htmlFor={id ?? ''}
            required={required}
            error={error}>
            <ReactSelect<Selectable, true>
                theme={theme}
                styles={styles}
                isMulti
                id={id}
                name={name}
                value={selectedOptions}
                placeholder={placeholder}
                className={'multi-select'}
                classNamePrefix="multi-select"
                hideSelectedOptions={false}
                closeMenuOnSelect={false}
                closeMenuOnScroll={false}
                onChange={handleOnChange}
                onBlur={onBlur}
                options={selectableOptions}
                components={{ Option: CheckboxOption }}
                inputValue={searchText}
                onInputChange={handleInputChange}
                getOptionValue={asSelectableValue}
                isDisabled={disabled}
            />
        </EntryWrapper>
    );
};
