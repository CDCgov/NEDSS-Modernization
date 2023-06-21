import { Dropdown, Grid, Label } from '@trussworks/react-uswds';

type SelectProps = {
    name?: string;
    htmlFor?: string;
    label?: string;
    id?: string;
    options: { name: string; value: string }[];
    onChange?: any;
    defaultValue?: string;
    isMulti?: boolean;
    dataTestid?: string;
    flexBox?: boolean;
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
    dataTestid,
    flexBox,
    ...props
}: SelectProps) => {
    const DropDown = () => {
        return (
            <Dropdown
                data-testid={dataTestid || 'dropdown'}
                multiple={isMulti}
                defaultValue={defaultValue}
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
        );
    };

    return (
        <>
            {flexBox ? (
                <Grid row>
                    <Grid col={6}>{label && <Label htmlFor={htmlFor || ''}>{label}</Label>}</Grid>
                    <Grid col={6}>
                        {defaultValue && <DropDown />}
                        {!defaultValue && <DropDown />}
                    </Grid>
                </Grid>
            ) : (
                <>
                    {label && <Label htmlFor={htmlFor || ''}>{label}</Label>}
                    {defaultValue && <DropDown />}
                    {!defaultValue && <DropDown />}
                </>
            )}
        </>
    );
};
