import { Control, Controller, FieldValues } from 'react-hook-form';
import { Checkbox } from '@trussworks/react-uswds';

type EventTypesProps = {
    control: Control<FieldValues, any>;
    name: string;
    label?: string;
    id?: string;
};

export const CheckBoxControl = ({ control, name, id, label }: EventTypesProps) => {
    return (
        <Controller
            control={control}
            name={name}
            render={({ field: { onChange, value } }) => (
                <Checkbox checked={value} onChange={onChange} id={id || ''} name={name} label={label} />
            )}
        />
    );
};
