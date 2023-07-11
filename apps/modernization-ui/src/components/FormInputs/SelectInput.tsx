import { Dropdown, Grid, Label, ErrorMessage } from '@trussworks/react-uswds';

type SelectProps = {
    htmlFor?: string;
    label?: string;
    options: { name: string; value: string }[];
    isMulti?: boolean;
    dataTestid?: string;
    flexBox?: boolean;
    error?: string;
} & JSX.IntrinsicElements['select'];

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
    onBlur,
    ...props
}: SelectProps) => {
    const DropDown = () => {
        return (
            <Dropdown
                onBlur={onBlur}
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
                    <Grid col={6}>
                        {label && (
                            <>
                                <Label htmlFor={htmlFor || ''}>{label}</Label>
                                <ErrorMessage id={`${error}-message`}>{error}</ErrorMessage>
                            </>
                        )}
                    </Grid>
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
