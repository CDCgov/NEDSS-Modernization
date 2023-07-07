import { Button, ButtonGroup, Grid } from '@trussworks/react-uswds';
import { Controller, FieldValues, useForm, useWatch } from 'react-hook-form';
import { SelectInput } from 'components/FormInputs/SelectInput';
import { DatePickerInput } from 'components/FormInputs/DatePickerInput';
import { useDetailedRaceCodedValues, useRaceCodedValues } from 'coded/race';
import { RaceEntry } from './RaceEntry';
import { externalizeDateTime } from 'date';
import { orNull } from 'utils';
import { MultiSelectInput } from 'components/selection/multi';

type EntryProps = {
    action: string;
    entry: RaceEntry;
    onChange: (updated: RaceEntry) => void;
    onCancel: () => void;
};

export const RaceEntryForm = ({ action, entry, onChange, onCancel }: EntryProps) => {
    const {
        handleSubmit,
        control,
        formState: { isValid }
    } = useForm({ mode: 'onBlur' });

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
        <div className="width-full maxw-full modal-form">
            <div className="modal-body">
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
                                    htmlFor={'asOf'}
                                    label="As of"
                                    errorMessage={error?.message}
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
            </div>
            <div className="border-top border-base-lighter padding-2 margin-left-auto">
                <ButtonGroup className="flex-justify-end">
                    <Button type="button" className="margin-top-0" data-testid="cancel-btn" outline onClick={onCancel}>
                        Go Back
                    </Button>
                    <Button
                        disabled={!isValid}
                        onClick={handleSubmit(onSubmit)}
                        type="submit"
                        className="padding-105 text-center margin-top-0">
                        {action}
                    </Button>
                </ButtonGroup>
            </div>
        </div>
    );
};
