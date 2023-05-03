import { Control, Controller, FieldValues } from 'react-hook-form';
import { Label } from '@trussworks/react-uswds';
import Select, { components } from 'react-select';
import { useState } from 'react';
import './MultiSelectControl.scss';

type EventTypesProps = {
    control: Control<FieldValues, any>;
    name: string;
    options: any;
    label?: string;
    defaultValue?: any;
};
const Option = (props: any) => {
    return (
        <div>
            <components.Option {...props}>
                <input type="checkbox" checked={props.isSelected} onChange={() => null} /> <label>{props.label}</label>
            </components.Option>
        </div>
    );
};

const MultiValue = (props: any) => (
    <components.MultiValue {...props}>
        <span> {props.data.label}</span>
    </components.MultiValue>
);

const DropdownIndicator = (props: any) => (
    // Remove arrow indicator from react-select
    <components.DropdownIndicator {...props}>
        <span></span>
    </components.DropdownIndicator>
);

const customStyles = {
    control: (base: any, state: any) => ({
        ...base,
        background: 'none',
        borderRadius: 0,
        borderColor: '#565c65',
        boxShadow: state.isFocused ? null : null,
        '&:hover': {
            borderColor: '#565c65'
        },
        outline: state.isFocused ? '0.25rem solid #2491ff' : 'none'
    }),
    indicatorSeparator: (base: any) => {
        return {
            ...base,
            backgroundColor: 'unset'
        };
    },
    multiValue: (base: any) => {
        return {
            ...base,
            color: 'white',
            backgroundColor: '#0096fb',
            borderRadius: '8px'
        };
    },
    multiValueLabel: (base: any) => {
        return {
            ...base,
            color: 'white'
        };
    },
    path: (base: any) => ({
        ...base,
        display: 'none !important'
    })
};

export const MultiSelectControl = ({ control, name, options, label }: EventTypesProps) => {
    const [selectedOptions, setSelectedOptions] = useState<any[]>([]);

    const handleOnChange = (e: any, onChange: any) => {
        const tempArr: any = [];
        e.map((re: any) => {
            tempArr.push(re.value);
        });
        setSelectedOptions(e);
        tempArr.length > 0 ? onChange(tempArr) : onChange(undefined);
    };

    return (
        <div className="multi-select-control">
            <Label htmlFor={name} className="margin-bottom-1">
                {label}
            </Label>
            <Controller
                control={control}
                name={name}
                render={({ field: { onChange } }) => (
                    <Select
                        value={selectedOptions}
                        placeholder="- Select -"
                        className="multiselect"
                        isMulti
                        name={name}
                        styles={customStyles}
                        hideSelectedOptions={false}
                        closeMenuOnSelect={false}
                        closeMenuOnScroll={false}
                        onChange={(e) => handleOnChange(e, onChange)}
                        options={options}
                        components={{ Option, MultiValue, DropdownIndicator }}
                    />
                )}
            />
        </div>
    );
};
