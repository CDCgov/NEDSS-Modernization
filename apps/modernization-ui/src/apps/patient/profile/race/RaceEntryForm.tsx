import { Grid } from '@trussworks/react-uswds';
import { Controller, FieldValues, useForm, useWatch } from 'react-hook-form';
import { SelectInput } from 'components/FormInputs/SelectInput';
import { DatePickerInput } from 'components/FormInputs/DatePickerInput';
import { EntryFooter } from 'apps/patient/profile/entry';
import { useDetailedRaceCodedValues, useRaceCodedValues } from 'coded/race';
import { RaceEntry } from './RaceEntry';
import { externalizeDateTime } from 'date';
import { orNull } from 'utils';
import { MultiSelectInput } from 'components/selection/multi';

type EntryProps = {
    entry: RaceEntry;
    onChange: (updated: RaceEntry) => void;
    onDelete?: () => void;
};

export const RaceEntryForm = ({ entry, onChange, onDelete }: EntryProps) => {
    const { handleSubmit, control } = useForm({ mode: 'onBlur' });

    const categories = useRaceCodedValues();

    const selectedCategory = useWatch({ control, name: 'category', defaultValue: entry.category });

    const detailedRaces = useDetailedRaceCodedValues(selectedCategory);

    const onSubmit = (entered: FieldValues) => {
        onChange({
            patient: entry.patient,
            asOf: externalizeDateTime(entered.asOf),
            category: orNull(entered.category),
            detailed: entered.detailed ? entered.detailed : []
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
                            defaultValue={entry.asOf}
                            rules={{ required: { value: true, message: 'As of date is required.' } }}
                            render={({ field: { onBlur, onChange, value }, fieldState: { error } }) => (
                                <DatePickerInput
                                    onBlur={onBlur}
                                    flexBox
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
                            name="category"
                            rules={{ required: { value: true, message: 'Race is required.' } }}
                            defaultValue={entry.category}
                            render={({ field: { onBlur, onChange, value }, fieldState: { error } }) => (
                                <SelectInput
                                    onBlur={onBlur}
                                    flexBox
                                    defaultValue={value}
                                    onChange={onChange}
                                    htmlFor={'category'}
                                    label="Race"
                                    options={categories}
                                    error={error?.message}
                                    required
                                />
                            )}
                        />
                    </Grid>
                    {detailedRaces.length > 0 && (
                        <Grid row col={12} className="flex-justify flex-align-center padding-2">
                            <Grid col={6} className="margin-top-1">
                                Detailed race:
                            </Grid>
                            <Grid col={6}>
                                <Controller
                                    control={control}
                                    name="detailed"
                                    shouldUnregister
                                    defaultValue={entry.detailed}
                                    render={({ field: { onChange, value } }) => (
                                        <MultiSelectInput
                                            id="detailed"
                                            value={value}
                                            onChange={onChange}
                                            options={detailedRaces}
                                        />
                                    )}
                                />
                            </Grid>
                        </Grid>
                    )}
                </Grid>
            </section>

            <EntryFooter onSave={handleSubmit(onSubmit)} onDelete={onDelete} />
        </>
    );
};
