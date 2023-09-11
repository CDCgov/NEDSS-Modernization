import { Dropdown } from '@trussworks/react-uswds';

import { EntryWrapper } from 'components/Entry';

type SelectProps = {
    htmlFor?: string;
    label?: string;
    options: { name: string; value: string }[];
    isMulti?: boolean;
    dataTestid?: string;
    flexBox?: boolean;
    error?: string;
    required?: boolean;
    defaultValue?: string | number | string[] | undefined | null;
} & Omit<JSX.IntrinsicElements['select'], 'defaultValue'>;

export const SelectInput = ({
    name,
    htmlFor,
    label,
    id,
    options,
    onChange,
    defaultValue,
    isMulti,
    dataTestid,
    flexBox,
    error,
    required,
    onBlur,
    ...props
}: SelectProps) => {
    const orientation = flexBox ? 'horizontal' : 'vertical';

    return (
        <EntryWrapper
            orientation={orientation}
            label={label || ''}
            htmlFor={htmlFor || ''}
            required={required}
            error={error}>
            <Dropdown
                onBlur={onBlur}
                data-testid={dataTestid || 'dropdown'}
                multiple={isMulti}
                defaultValue={defaultValue || undefined}
                placeholder="-Select-"
                onChange={onChange}
                {...props}
                id={id || ''}
                name={name || ''}>
                <>
                    <option value="">- Select -</option>
                    {options?.map((item, index) => (
                        <option key={index} value={item.value}>
                            {item.name}
                        </option>
                    ))}
                </>
            </Dropdown>
        </EntryWrapper>
    );
};
