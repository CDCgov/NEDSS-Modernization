import ReactSelect, { MultiValue, components } from 'react-select';
import { FocusEventHandler, useEffect, useMemo, useState } from 'react';
import './MultiSelectInput.scss';
import { mapNonNull } from 'utils';
import { EntryWrapper } from 'components/Entry';

const CheckedOption = (props: any) => {
    return (
        <div>
            <components.Option {...props}>
                <input type="checkbox" checked={props.isSelected} readOnly /> <label>{props.label}</label>
            </components.Option>
        </div>
    );
};

const asSelectable = (selectables: Selectable[]) => (item: string) =>
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
    error,
    placeholder = '- Select -',
    orientation = 'vertical'
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
                <ReactSelect
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
                    options={selectableOptions}
                    components={{ Input, Option: CheckedOption }}
                />
            </EntryWrapper>
        </div>
    );
};
