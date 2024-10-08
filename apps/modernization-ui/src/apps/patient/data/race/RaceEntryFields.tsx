import { useDetailedRaceCodedValues } from 'coded/race';
import { Controller, useFormContext, useWatch } from 'react-hook-form';
import { DatePickerInput } from 'components/FormInputs/DatePickerInput';
import { RaceEntry } from './entry';
import { MultiSelect, SingleSelect } from 'design-system/select';
import { Selectable } from 'options';

type RaceEntryFieldsProps = {
    categories: Selectable[];
};

const RaceEntryFields = ({ categories }: RaceEntryFieldsProps) => {
    const { control } = useFormContext<RaceEntry>();

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
                        label="Race as of"
                        onBlur={onBlur}
                        orientation="horizontal"
                        defaultValue={value}
                        onChange={onChange}
                        name={`race-${name}`}
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
                        id={`race-${name}`}
                        name={name}
                        options={categories}
                        error={error?.message}
                    />
                )}
            />

            <Controller
                control={control}
                name="detailed"
                shouldUnregister
                render={({ field: { onChange, value, name } }) => (
                    <MultiSelect
                        label="Detailed race"
                        orientation="horizontal"
                        id={`race-${name}`}
                        name={name}
                        disabled={selectedCategory === undefined}
                        value={value}
                        onChange={onChange}
                        options={detailedRaces}
                    />
                )}
            />
        </section>
    );
};

export { RaceEntryFields };

export type { RaceEntryFieldsProps };
