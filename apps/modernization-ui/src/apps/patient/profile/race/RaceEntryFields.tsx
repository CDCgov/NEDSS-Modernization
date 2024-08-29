import { useDetailedRaceCodedValues, useRaceCodedValues } from 'coded/race';
import { Controller, useFormContext, useWatch } from 'react-hook-form';
import { RaceEntry } from './RaceEntry';
import { validateCategory } from './validateCategory';
import { DatePickerInput } from 'components/FormInputs/DatePickerInput';
import { SelectInput } from 'components/FormInputs/SelectInput';
import { MultiSelectInput } from 'components/selection/multi';

type Props = {
    patient: number;
    editing?: boolean;
};
export const RaceEntryFields = ({ patient, editing }: Props) => {
    const { control } = useFormContext<RaceEntry>();
    const categories = useRaceCodedValues();
    const selectedCategory = useWatch({ control, name: 'category' });
    const detailedRaces = useDetailedRaceCodedValues(selectedCategory);

    return (
        <section>
            <Controller
                control={control}
                name="asOf"
                rules={{ required: { value: true, message: 'As of date is required.' } }}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <DatePickerInput
                        label="As of:"
                        onBlur={onBlur}
                        orientation="horizontal"
                        defaultValue={value}
                        onChange={onChange}
                        name={name}
                        disableFutureDates
                        errorMessage={error?.message}
                        required
                    />
                )}
            />
            <Controller
                control={control}
                name="category"
                rules={{
                    required: { value: true, message: 'Race is required.' },
                    validate: validateCategory(patient)
                }}
                render={({ field: { onBlur, onChange, name, value }, fieldState: { error } }) => (
                    <SelectInput
                        label="Race:"
                        orientation="horizontal"
                        required
                        onBlur={onBlur}
                        onChange={onChange}
                        defaultValue={value}
                        htmlFor={name}
                        options={categories}
                        error={error?.message}
                        disabled={editing}
                    />
                )}
            />
            {detailedRaces.length > 0 && (
                <Controller
                    control={control}
                    name="detailed"
                    shouldUnregister
                    render={({ field: { onChange, value, name } }) => (
                        <MultiSelectInput
                            label="Detailed race:"
                            orientation="horizontal"
                            id={name}
                            name={name}
                            value={value}
                            onChange={onChange}
                            options={detailedRaces}
                        />
                    )}
                />
            )}
        </section>
    );
};
