import { Button, ButtonGroup, Grid } from '@trussworks/react-uswds';
import { Controller, FieldValues, useForm, useWatch } from 'react-hook-form';
import { SelectInput } from 'components/FormInputs/SelectInput';
import { DatePickerInput } from 'components/FormInputs/DatePickerInput';
import { useDetailedRaceCodedValues, useRaceCodedValues } from 'coded/race';
import { RaceEntry } from './RaceEntry';

type EntryProps = {
    action: string;
    entry: RaceEntry;
    onChange: (updated: RaceEntry) => void;
    onCancel: () => void;
};

export const RaceEntryForm = ({ action, entry, onChange, onCancel }: EntryProps) => {
    const { handleSubmit, control } = useForm();

    const categories = useRaceCodedValues();

    const selectedCategory = useWatch({ control, name: 'category' });

    const detailedRaces = useDetailedRaceCodedValues(selectedCategory);

    const onSubmit = (entered: FieldValues) => {
        onChange({
            patient: entry.patient,
            asOf: entered.asOf,
            category: entered.category,
            detailed: entered.detailed ? [entered.detailed] : []
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
                            rules={{ required: true }}
                            defaultValue={entry.asOf}
                            render={({ field: { onChange, value } }) => (
                                <DatePickerInput
                                    flexBox
                                    defaultValue={value}
                                    onChange={onChange}
                                    name="asOf"
                                    htmlFor={'asOf'}
                                    label="As of"
                                />
                            )}
                        />
                    </Grid>
                    <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
                        <Controller
                            control={control}
                            name="category"
                            rules={{ required: true }}
                            defaultValue={entry.category}
                            render={({ field: { onChange, value } }) => (
                                <SelectInput
                                    flexBox
                                    defaultValue={value}
                                    onChange={onChange}
                                    htmlFor={'category'}
                                    label="Race"
                                    options={categories}
                                />
                            )}
                        />
                    </Grid>
                    {detailedRaces.length > 0 && (
                        <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
                            <Controller
                                control={control}
                                name="detailed"
                                defaultValue={entry.detailed[0]}
                                render={({ field: { onChange, value } }) => (
                                    <SelectInput
                                        flexBox
                                        onChange={onChange}
                                        defaultValue={value}
                                        label="Detailed race"
                                        name="detailed"
                                        htmlFor="detailed"
                                        id="detailed"
                                        options={detailedRaces}
                                    />
                                )}
                            />
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
