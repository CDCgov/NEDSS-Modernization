import { Grid } from '@trussworks/react-uswds';
import { Controller, useForm, useWatch } from 'react-hook-form';
import { SelectInput } from 'components/FormInputs/SelectInput';
import { DatePickerInput } from 'components/FormInputs/DatePickerInput';
import { EntryFooter } from 'apps/patient/profile/entry';
import { useDetailedRaceCodedValues, useRaceCodedValues } from 'coded/race';
import { RaceEntry } from './RaceEntry';

import { MultiSelectInput } from 'components/selection/multi';
import { validateCategory } from './validateCategory';

type EntryProps = {
    patient: number;
    entry: Partial<RaceEntry>;
    onChange: (updated: RaceEntry) => void;
    onDelete?: () => void;
};

export const RaceEntryForm = ({ patient, entry, onChange, onDelete }: EntryProps) => {
    const { handleSubmit, control } = useForm<RaceEntry, Partial<RaceEntry>>({ mode: 'onBlur' });

    const categories = useRaceCodedValues();

    const selectedCategory = useWatch({ control, name: 'category', defaultValue: entry.category });

    const detailedRaces = useDetailedRaceCodedValues(selectedCategory);

    const updating = typeof entry.category === 'string';

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
                            rules={{
                                required: { value: true, message: 'Race is required.' },
                                validate: validateCategory(patient, entry.category)
                            }}
                            defaultValue={entry.category}
                            render={({ field: { onBlur, onChange, name, value }, fieldState: { error } }) => (
                                <SelectInput
                                    flexBox
                                    required
                                    onBlur={onBlur}
                                    onChange={onChange}
                                    defaultValue={value}
                                    htmlFor={name}
                                    label="Race"
                                    options={categories}
                                    error={error?.message}
                                    disabled={updating}
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

            <EntryFooter onSave={handleSubmit(onChange)} onDelete={onDelete} />
        </>
    );
};
