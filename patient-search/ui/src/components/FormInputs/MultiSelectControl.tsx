import { Control, Controller, FieldValues } from 'react-hook-form';
import { Icon, Label } from '@trussworks/react-uswds';
import Multiselect from 'multiselect-react-dropdown';

type EventTypesProps = {
    control: Control<FieldValues, any>;
    name: string;
    options: any;
    label?: string;
    defaultValue?: any;
};

export const MultiSelectControl = ({ control, name, options, label, defaultValue }: EventTypesProps) => {
    const handleOnChange = (e: any, onChange: any) => {
        const tempArr: any = [];
        e.map((re: any) => {
            tempArr.push(re.value);
        });
        tempArr.length > 0 ? onChange(tempArr) : onChange(undefined);
    };

    const handleSelectedOptions = (value: any) => {
        const tempArr: any = [];
        options.map((item: any) => {
            value?.map((re: any) => {
                item.value === re && tempArr.push(item);
            });
        });
        console.log(value);
        return tempArr;
    };

    return (
        <>
            <Label htmlFor={name} className="margin-bottom-1">
                {label}
            </Label>
            <Controller
                control={control}
                name={name}
                render={({ field: { onChange, value } }) => (
                    <Multiselect
                        selectedValues={handleSelectedOptions(defaultValue)}
                        placeholder="- Select -"
                        className="multiselect"
                        customCloseIcon={
                            <Icon.Close className="margin-left-05 text-bold" style={{ cursor: 'pointer' }} />
                        }
                        displayValue="name"
                        onRemove={(e) => handleOnChange(e, onChange)}
                        onSelect={(e) => handleOnChange(e, onChange)}
                        options={options}
                    />
                )}
            />
        </>
    );
};
