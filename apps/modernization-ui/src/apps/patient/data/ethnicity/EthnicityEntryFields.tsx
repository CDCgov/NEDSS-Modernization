import { usePatientEthnicityCodedValues } from 'apps/patient/profile/ethnicity';
import { DatePickerInput } from 'components/FormInputs/DatePickerInput';
import { MultiSelect, SingleSelect } from 'design-system/select';
import { useEffect } from 'react';
import { Controller, useFormContext, useWatch } from 'react-hook-form';
import { EthnicityEntry } from './entry';

const UNKNOWN = 'UNK';
const HISPANIC = '2135-2';

export const EthnicityEntryFields = () => {
    const { control, setValue } = useFormContext<{ ethnicity: EthnicityEntry }>();
    const coded = usePatientEthnicityCodedValues();
    const selectedEthnicity = useWatch({ control, name: 'ethnicity.ethnicGroup' });

    useEffect(() => {
        setValue('ethnicity.unknownReason', undefined);
        setValue('ethnicity.detailed', []);
    }, [selectedEthnicity]);

    return (
        <section>
            <Controller
                control={control}
                name="ethnicity.asOf"
                rules={{ required: { value: true, message: 'As of date is required.' } }}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <DatePickerInput
                        label="Ethnicity information as of"
                        orientation="horizontal"
                        defaultValue={value}
                        onBlur={onBlur}
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
                name="ethnicity.ethnicGroup"
                render={({ field: { onChange, onBlur, value, name } }) => (
                    <SingleSelect
                        label="Ethnicity"
                        orientation="horizontal"
                        onChange={onChange}
                        onBlur={onBlur}
                        id={name}
                        name={name}
                        value={value}
                        options={coded.ethnicGroups}
                    />
                )}
            />

            {selectedEthnicity?.value === HISPANIC && (
                <Controller
                    control={control}
                    name="ethnicity.detailed"
                    shouldUnregister
                    render={({ field: { onChange, onBlur, value, name } }) => (
                        <MultiSelect
                            label="Spanish origin"
                            orientation="horizontal"
                            onChange={onChange}
                            onBlur={onBlur}
                            id={name}
                            name={name}
                            value={value}
                            options={coded.detailedEthnicities}
                        />
                    )}
                />
            )}
            {selectedEthnicity?.value === UNKNOWN && (
                <Controller
                    control={control}
                    name="ethnicity.unknownReason"
                    shouldUnregister
                    render={({ field: { onChange, onBlur, value, name } }) => (
                        <SingleSelect
                            label="Reason unknown"
                            orientation="horizontal"
                            value={value}
                            onChange={onChange}
                            onBlur={onBlur}
                            id={name}
                            name={name}
                            options={coded.ethnicityUnknownReasons}
                        />
                    )}
                />
            )}
        </section>
    );
};
