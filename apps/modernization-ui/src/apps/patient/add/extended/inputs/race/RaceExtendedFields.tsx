import { RaceEntry } from "apps/patient/data/entry";
import { DatePickerInput } from "components/FormInputs/DatePickerInput";
import { SelectInput } from "components/FormInputs/SelectInput";
import { MultiSelectInput } from "components/selection/multi";
import { Selectable } from "options/selectable";
import { Controller, useFormContext, useWatch } from "react-hook-form";

const getRaceCodedValues = () : Selectable[] => {

}

const getDetailedRaceCodedValues = (selectedCategory : Selectable) : Selectable[] => {

}


export const RaceExtendedFields = () => {
  const {control} = useFormContext<RaceEntry>();
    const categories = getRaceCodedValues();
    const selectedCategory = useWatch({ control, name: 'race' });
    const detailedRaces = getDetailedRaceCodedValues(selectedCategory);

    // const handleCategoryValidation = () => {
    //     if (patient) {
    //         return validateCategory(patient);
    //     }
    // };

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
                <SelectInput
                    label="Race"
                    orientation="horizontal"
                    required
                    onBlur={onBlur}
                    onChange={onChange}
                    defaultValue={value.name}
                    htmlFor={name}
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
                    <MultiSelectInput
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
);)
}