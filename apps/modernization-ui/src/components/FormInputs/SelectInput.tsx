import { Dropdown, Label } from '@trussworks/react-uswds';

type SelectProps = {
    name?: string;
    htmlFor?: string;
    label?: string;
    id?: string;
    options: { name: string; value: string }[];
    onChange?: any;
    defaultValue?: string;
    isMulti?: boolean;
};

export const SelectInput = ({
    name,
    htmlFor,
    label,
    id,
    options,
    onChange,
    defaultValue,
    isMulti,
    ...props
}: SelectProps) => {
    return (
        <>
            {label && <Label htmlFor={htmlFor || ''}>{label}</Label>}
            {defaultValue && (
                <Dropdown
                    multiple={isMulti}
                    defaultValue={defaultValue}
                    placeholder="-Select-"
                    onChange={onChange}
                    {...props}
                    id={id || ''}
                    name={name || ''}>
                    <>
                        <option>- Select -</option>
                        {options?.map((item, index) => (
                            <option key={index} value={item.value}>
                                {item.name}
                            </option>
                        ))}
                    </>
                </Dropdown>
            )}
            {!defaultValue && (
                <Dropdown
                    multiple={isMulti}
                    placeholder="-Select-"
                    onChange={onChange}
                    {...props}
                    id={id || ''}
                    name={name || ''}>
                    <>
                        <option>- Select -</option>
                        {options?.map((item, index) => (
                            <option key={index} value={item.value}>
                                {item.name}
                            </option>
                        ))}
                    </>
                </Dropdown>
            )}
        </>
    );
};
