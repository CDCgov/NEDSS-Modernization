import { Control, Controller, FieldValues } from 'react-hook-form';
import { SelectInput } from './SelectInput';

type EventTypesProps = {
    control: Control<FieldValues, any>;
    name: string;
    onChangeMethod?: (event: any) => void;
    options: any;
    label?: string;
};

export const SelectControl = ({ control, name, onChangeMethod, options, label }: EventTypesProps) => {
    return (
        <Controller
            control={control}
            name={name}
            render={({ field: { onChange } }) => (
                <SelectInput
                    onChange={(e: any) => {
                        onChangeMethod?.(e.target.value);
                        onChange();
                    }}
                    label={label}
                    options={options}
                />
            )}
        />
    );
};
