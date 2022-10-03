import { Dropdown, Label } from '@trussworks/react-uswds';

type SelectProps = {
    name?: string;
    htmlFor?: string;
    label?: string;
    id?: string;
    options: { name: string; value: string }[];
    onChange?: any;
};

export const SelectInput = ({ name, htmlFor, label, id, options, onChange, ...props }: SelectProps) => {
    return (
        <>
            <Label htmlFor={htmlFor || ''}>{label}</Label>
            <Dropdown onChange={onChange} {...props} className="bg-base-lightest" id={id || ''} name={name || ''}>
                <>
                    <option>- Select -</option>
                    {options?.map((item, index) => (
                        <option key={index} value={item.name.toLowerCase()}>
                            {item.name}
                        </option>
                    ))}
                </>
            </Dropdown>
        </>
    );
};
