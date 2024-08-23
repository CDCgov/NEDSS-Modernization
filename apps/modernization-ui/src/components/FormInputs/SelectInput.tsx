import { Dropdown } from '@trussworks/react-uswds';

import { EntryWrapper, Orientation } from 'components/Entry';

export type Selectable = { name: string; value: string };

type SelectProps = {
    htmlFor?: string;
    label?: string;
    options: Selectable[];
    dataTestid?: string;
    flexBox?: boolean;
    orientation?: Orientation;
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
    error,
    required,
    onBlur,
    ...props
}: SelectProps) => {
    const orientation = flexBox ? 'horizontal' : props.orientation;

    //  In order for the defaultValue to be applied the component has to be re-created when it goes from null to non null.
    const Wrapped = () => (
        <Dropdown
            data-testid={dataTestid || 'dropdown'}
            id={id || ''}
            name={name || ''}
            defaultValue={defaultValue || undefined}
            placeholder="-Select-"
            onChange={onChange}
            onBlur={onBlur}
            {...props}>
            {renderOptions(options)}
        </Dropdown>
    );

    return (
        <EntryWrapper
            orientation={orientation}
            label={label || ''}
            htmlFor={htmlFor || ''}
            required={required}
            error={error}>
            {defaultValue && <Wrapped />}
            {!defaultValue && <Wrapped />}
        </EntryWrapper>
    );
};
