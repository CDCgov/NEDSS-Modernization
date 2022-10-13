import { Control, Controller, FieldValues } from 'react-hook-form';
import { EventType } from '../../generated/graphql/schema';
import { SelectInput } from '../FormInputs/SelectInput';

type EventTypesProps = {
    control: Control<FieldValues, any>;
    name: string;
    onChangeMethod: (event: any) => void;
};

export const EventTypes = ({ control, name, onChangeMethod }: EventTypesProps) => {
    return (
        <Controller
            control={control}
            name={name}
            render={({ field: { onChange } }) => (
                <SelectInput
                    onChange={(e: any) => {
                        onChangeMethod(e.target.value);
                        onChange();
                    }}
                    label="Event Type:"
                    options={[
                        { name: 'Investigation', value: EventType.Investigation },
                        { name: 'Laboratory Report', value: EventType.LaboratoryReport }
                    ]}
                />
            )}
        />
    );
};
