import { Control, Controller, FieldValues } from 'react-hook-form';
import { SelectInput } from './SelectInput';

type EventTypesProps = {
    control: Control<FieldValues, any>;
    name: string;
    onChangeMethod?: (event: any) => void;
    options: any;
    label?: string;
    isMulti?: boolean;
};

export const SelectControl = ({ control, name, onChangeMethod, options, label, isMulti }: EventTypesProps) => {
    return (
        <Controller
            control={control}
            name={name}
            render={({ field: { onChange } }) => (
                <SelectInput isMulti={isMulti} onChange={onChange} label={label} options={options} />
            )}
        />
    );
};
