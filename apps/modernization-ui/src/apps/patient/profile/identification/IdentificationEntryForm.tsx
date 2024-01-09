import { Grid } from '@trussworks/react-uswds';
import { Controller, FieldValues, useForm } from 'react-hook-form';
import { DatePickerInput } from 'components/FormInputs/DatePickerInput';
import { SelectInput } from 'components/FormInputs/SelectInput';
import { Input } from 'components/FormInputs/Input';
import { IdentificationEntry } from './identification';
import { EntryFooter } from 'apps/patient/profile/entry';
import { usePatientIdentificationCodedValues } from './usePatientIdentificationCodedValues';
import { externalizeDateTime } from 'date';
import { orNull } from 'utils';
import { maxLengthRule } from 'validation/entry';

type Props = {
    entry: IdentificationEntry;
    onChange: (updated: IdentificationEntry) => void;
    onDelete?: () => void;
};

export const IdentificationEntryForm = ({ entry, onChange, onDelete }: Props) => {
    const { handleSubmit, control } = useForm({ mode: 'onBlur' });

    const coded = usePatientIdentificationCodedValues();

    const onSubmit = (entered: FieldValues) => {
        onChange({
            patient: entry.patient,
            sequence: entry.sequence,
            asOf: externalizeDateTime(entered.asOf),
            type: orNull(entered.type),
            value: orNull(entered.id),
            state: orNull(entered.state)
        });
    };

    return (
        <>
            <section>
                <Grid row>
                    <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
                        <Controller
                            control={control}
                            name="asOf"
                            defaultValue={entry?.asOf}
                            rules={{ required: { value: true, message: 'As of date is required.' } }}
                            render={({ field: { onBlur, onChange, value }, fieldState: { error } }) => (
                                <DatePickerInput
                                    flexBox
                                    onBlur={onBlur}
                                    defaultValue={value}
                                    onChange={onChange}
                                    name="asOf"
                                    disableFutureDates
                                    htmlFor={'asOf'}
                                    label="As of"
                                    errorMessage={error?.message}
                                    required
                                />
                            )}
                        />
                    </Grid>
                    <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
                        <Controller
                            control={control}
                            name="type"
                            defaultValue={entry?.type}
                            rules={{ required: { value: true, message: 'Type is required.' } }}
                            render={({ field: { onBlur, onChange, value }, fieldState: { error } }) => (
                                <SelectInput
                                    flexBox
                                    defaultValue={value}
                                    onBlur={onBlur}
                                    onChange={onChange}
                                    htmlFor={'type'}
                                    label="Type"
                                    options={coded.types}
                                    error={error?.message}
                                    required
                                />
                            )}
                        />
                    </Grid>
                    <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
                        <Controller
                            control={control}
                            name="id"
                            defaultValue={entry.value}
                            rules={{ required: { value: true, message: 'ID # is required.' }, ...maxLengthRule(100) }}
                            render={({ field: { onBlur, onChange, value }, fieldState: { error } }) => (
                                <Input
                                    flexBox
                                    onBlur={onBlur}
                                    onChange={onChange}
                                    defaultValue={value}
                                    type="text"
                                    label="ID #"
                                    name="id"
                                    htmlFor="id"
                                    id="id"
                                    error={error?.message}
                                    required
                                />
                            )}
                        />
                    </Grid>
                    <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
                        <Controller
                            control={control}
                            name="state"
                            defaultValue={entry?.state}
                            render={({ field: { onChange, value } }) => (
                                <SelectInput
                                    flexBox
                                    defaultValue={value}
                                    onChange={onChange}
                                    htmlFor={'state'}
                                    label="Issued state"
                                    options={coded.authorities}
                                />
                            )}
                        />
                    </Grid>
                </Grid>
            </section>

            <EntryFooter onSave={handleSubmit(onSubmit)} onDelete={onDelete} />
        </>
    );
};
