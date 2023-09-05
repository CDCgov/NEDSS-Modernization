import { Label } from '@trussworks/react-uswds';
import { Maybe } from 'generated/graphql/schema';
import { JSXElementConstructor, ReactElement } from 'react';
import { Control, Controller, Path } from 'react-hook-form';
import Select, {
    CSSObjectWithLabel,
    ControlProps,
    DropdownIndicatorProps,
    GroupBase,
    MultiValueProps,
    OptionProps,
    components
} from 'react-select';
import './MultiSelectControl.scss';

const customStyles = {
    control: (base: CSSObjectWithLabel, state: ControlProps<Option>): CSSObjectWithLabel => ({
        ...base,
        background: 'none',
        borderRadius: 0,
        borderColor: '#565c65',
        boxShadow: undefined,
        '&:hover': {
            borderColor: '#565c65'
        },
        outline: state.isFocused ? '0.25rem solid #2491ff' : 'none'
    }),

    indicatorSeparator: (base: CSSObjectWithLabel): CSSObjectWithLabel => {
        return {
            ...base,
            backgroundColor: 'unset'
        };
    },
    multiValue: (base: CSSObjectWithLabel): CSSObjectWithLabel => {
        return {
            ...base,
            color: 'white',
            backgroundColor: '#0096fb',
            borderRadius: '8px'
        };
    },
    multiValueLabel: (base: CSSObjectWithLabel): CSSObjectWithLabel => {
        return {
            ...base,
            color: 'white'
        };
    },
    path: (base: CSSObjectWithLabel): CSSObjectWithLabel => ({
        ...base,
        display: 'none !important'
    })
};

const OptionComponent = (props: OptionProps<Option, true, GroupBase<Option>>) => (
    <components.Option {...props}>
        <input type="checkbox" checked={props.isSelected} onChange={() => null} /> <label>{props.label}</label>
    </components.Option>
);

const MultiValue = (props: MultiValueProps<Option, true, GroupBase<Option>>) => (
    <components.MultiValue {...props}>
        <span> {props.data.label}</span>
    </components.MultiValue>
);

const DropdownIndicator = (props: DropdownIndicatorProps<Option, true, GroupBase<Option>>) => (
    <components.DropdownIndicator {...props}>
        <span></span>
    </components.DropdownIndicator>
);

type Field = Record<string, any>;

type MultiSelectProps<TFieldValues extends Field = Field, TName extends Path<TFieldValues> = Path<TFieldValues>> = {
    name: TName;
    control?: Control<TFieldValues>;
    label: string;
    options: Option[];
};

type Option = {
    label: Maybe<string> | undefined;
    value: string;
};

export const MultiSelectControlA: <
    TFieldValues extends Field = Field,
    TName extends Path<TFieldValues> = Path<TFieldValues>
>(
    props: MultiSelectProps<TFieldValues, TName>
) => ReactElement<any, string | JSXElementConstructor<any>> = ({ label, name, control, options }) => {
    return (
        <div className="multi-select-control">
            <Label htmlFor={name} className="margin-bottom-1">
                {label}
            </Label>
            <Controller
                control={control}
                name={name}
                render={({ field: { onChange, name } }) => (
                    <Select
                        placeholder="- Select -"
                        className="multiselect"
                        isMulti
                        name={name}
                        hideSelectedOptions={false}
                        closeMenuOnSelect={false}
                        closeMenuOnScroll={false}
                        onChange={(e) => onChange(e.map((e) => e.value))}
                        options={options}
                        styles={customStyles}
                        components={{ Option: OptionComponent, DropdownIndicator, MultiValue }}
                    />
                )}
            />
        </div>
    );
};
