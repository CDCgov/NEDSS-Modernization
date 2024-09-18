import { useDetailedRaceCodedValues, useRaceCodedValues } from 'coded/race';
import { Controller, useFormContext, useWatch } from 'react-hook-form';
import { DatePickerInput } from 'components/FormInputs/DatePickerInput';
import { RaceEntry } from '../entry';
import { MultiSelect, SingleSelect } from 'design-system/select';

export const RaceEntryFields = () => {
    const { control } = useFormContext<RaceEntry>();
    const categories = useRaceCodedValues();
    const selectedCategory = useWatch({ control, name: 'race' });
    const detailedRaces = useDetailedRaceCodedValues(selectedCategory?.value);

    return (
        <section>
            <Controller
                control={control}
                name="asOf"
                rules={{ required: { value: true, message: 'As of date is required.' } }}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <DatePickerInput
                        label="As of"
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
                name="race"
                rules={{
                    required: { value: true, message: 'Race is required.' }
                }}
                render={({ field: { onBlur, onChange, name, value }, fieldState: { error } }) => (
                    <SingleSelect
                        label="Race"
                        orientation="horizontal"
                        required
                        onBlur={onBlur}
                        onChange={onChange}
                        value={value}
                        id={name}
                        name={name}
                        options={categories}
                        error={error?.message}
                    />
                )}
            />
            {detailedRaces.length > 0 && (
                <Controller
                    control={control}
                    name="detailed"
                    shouldUnregister
                    render={({ field: { onChange, value, name } }) => (
                        <MultiSelect
                            label="Detailed race"
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
