import { Control, Controller, FieldValues } from 'react-hook-form';
import { SEARCH_TYPE } from '../../pages/advancedSearch/AdvancedSearch';
import { SelectInput } from '../FormInputs/SelectInput';

type EventTypesProps = {
    control: Control<FieldValues, any>;
    name: string;
    onChangeMethod: (event: any) => void;
    defaultValue?: any;
};

export const EventTypes = ({ control, name, defaultValue, onChangeMethod }: EventTypesProps) => {
    return (
        <Controller
            control={control}
            name={name}
            render={({ field: { onChange } }) => (
                <SelectInput
                    defaultValue={defaultValue}
                    onChange={(e: any) => {
                        onChangeMethod(e.target.value);
                        onChange();
                    }}
                    label="Event Type:"
                    options={[
                        { name: 'Investigation', value: SEARCH_TYPE.INVESTIGATION },
                        { name: 'Laboratory Report', value: SEARCH_TYPE.LAB_REPORT }
                    ]}
                />
            )}
        />
    );
};
