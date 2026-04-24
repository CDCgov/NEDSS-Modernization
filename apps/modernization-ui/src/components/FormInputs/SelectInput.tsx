import { Select } from '@trussworks/react-uswds';

import { EntryWrapper, Orientation, Sizing } from 'components/Entry';
import { useId } from 'react';

export type Selectable = { name: string; value: string };

type SelectProps = {
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

const Options = ({ options }: { options: Selectable[] }) => (
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
    const defaultId = useId();
    //  In order for the defaultValue to be applied the component has to be re-created when it goes from null to non null.
    const Wrapped = () => (
        <Select
            data-testid={dataTestid || 'dropdown'}
            id={id || defaultId}
            name={name || ''}
            defaultValue={defaultValue ?? undefined}
            required={required}
            onChange={onChange}
            onBlur={onBlur}
            {...props}
        >
            <Options options={options} />
        </Select>
    );

    return (
        <EntryWrapper
            orientation={flexBox ? 'horizontal' : orientation}
            label={label || ''}
            htmlFor={id || defaultId}
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
