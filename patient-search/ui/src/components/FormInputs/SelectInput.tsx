import { Dropdown, Label } from '@trussworks/react-uswds';

type SelectProps = {
    name?: string;
    htmlFor?: string;
    label?: string;
    id?: string;
    options: { name: string; value: string }[];
    onChange?: any;
    defaultValue?: string;
};

export const SelectInput = ({ name, htmlFor, label, id, options, onChange, defaultValue, ...props }: SelectProps) => {
    return (
        <>
            <Label htmlFor={htmlFor || ''}>{label}</Label>
            {defaultValue && (
                <Dropdown
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
                <Dropdown placeholder="-Select-" onChange={onChange} {...props} id={id || ''} name={name || ''}>
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
