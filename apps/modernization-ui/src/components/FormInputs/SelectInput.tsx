import { Select } from '@trussworks/react-uswds';

import { EntryWrapper, Orientation, Sizing } from 'components/Entry';

export type Selectable = { name: string; value: string };

type SelectProps = {
    htmlFor?: string;
    label?: string;
    options: Selectable[];
    dataTestid?: string;
    flexBox?: boolean;
    orientation?: Orientation;
    sizing?: Sizing;
    helperText?: string;
    error?: string;
    required?: boolean;
    defaultValue?: string | number | undefined | null;
} & Omit<JSX.IntrinsicElements['select'], 'defaultValue'>;

const renderOptions = (options: Selectable[]) => (
    <>
        <option value="">- Select -</option>
        {options?.map((item, index) => (
            <option key={index} value={item.value}>
                {item.name}
            </option>
        ))}
    </>
);

export const SelectInput = ({
    name,
    htmlFor,
    label,
    id,
    options,
    onChange,
    defaultValue,
    dataTestid,
    flexBox,
    orientation,
    sizing,
    helperText,
    error,
    required,
    onBlur,
    ...props
}: SelectProps) => {
    //  In order for the defaultValue to be applied the component has to be re-created when it goes from null to non null.
    const Wrapped = () => (
        <Select
            data-testid={dataTestid || 'dropdown'}
            id={id || ''}
            name={name || ''}
            defaultValue={defaultValue ?? undefined}
            onChange={onChange}
            onBlur={onBlur}
            {...props}
        >
            {renderOptions(options)}
        </Select>
    );

    return (
        <EntryWrapper
            orientation={flexBox ? 'horizontal' : orientation}
            label={label || ''}
            htmlFor={htmlFor || ''}
            required={required}
            sizing={sizing}
            helperText={helperText}
            error={error}
        >
            {defaultValue && <Wrapped />}
            {!defaultValue && <Wrapped />}
        </EntryWrapper>
    );
};
