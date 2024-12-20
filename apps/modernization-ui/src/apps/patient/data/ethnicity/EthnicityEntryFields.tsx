import { useEffect } from 'react';
import { Controller, useFormContext, useWatch } from 'react-hook-form';
import { DatePickerInput, validDateRule } from 'design-system/date';
import { MultiSelect, SingleSelect } from 'design-system/select';
import { EntryFieldsProps } from 'design-system/entry';
import { validateRequiredRule } from 'validation/entry';
import { usePatientEthnicityCodedValues } from 'apps/patient/profile/ethnicity';
import { EthnicityEntry } from './entry';

const UNKNOWN = 'UNK';
const HISPANIC = '2135-2';

const AS_OF_DATE_LABEL = 'Ethnicity information as of';

type EthnicityEntryFieldsProps = EntryFieldsProps;

export const EthnicityEntryFields = ({ orientation = 'horizontal' }: EthnicityEntryFieldsProps) => {
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
                rules={{ ...validateRequiredRule(AS_OF_DATE_LABEL), ...validDateRule(AS_OF_DATE_LABEL) }}
                render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                    <DatePickerInput
                        id={name}
                        label="Ethnicity information as of"
                        orientation={orientation}
                        value={value}
                        onBlur={onBlur}
                        onChange={onChange}
                        error={error?.message}
                        required
                        sizing="compact"
                    />
                )}
            />
            <Controller
                control={control}
                name="ethnicity.ethnicGroup"
                render={({ field: { onChange, onBlur, value, name } }) => (
                    <SingleSelect
                        label="Ethnicity"
                        orientation={orientation}
                        onChange={onChange}
                        onBlur={onBlur}
                        id={name}
                        name={name}
                        value={value}
                        options={coded.ethnicGroups}
                        sizing="compact"
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
                            orientation={orientation}
                            onChange={onChange}
                            onBlur={onBlur}
                            id={name}
                            name={name}
                            value={value}
                            options={coded.detailedEthnicities}
                            sizing="compact"
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
                            orientation={orientation}
                            value={value}
                            onChange={onChange}
                            onBlur={onBlur}
                            id={name}
                            name={name}
                            options={coded.ethnicityUnknownReasons}
                            sizing="compact"
                        />
                    )}
                />
            )}
        </section>
    );
};
