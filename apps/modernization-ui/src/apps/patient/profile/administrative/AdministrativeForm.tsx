import { Grid, Label, Textarea, ErrorMessage } from '@trussworks/react-uswds';
import { Controller, FieldValues, useForm } from 'react-hook-form';
import { DatePickerInput } from 'components/FormInputs/DatePickerInput';
import { EntryFooter } from 'apps/patient/profile/entry';
import { AdministrativeEntry } from './AdministrativeEntry';
import { maxLengthRule } from 'validation/entry';

type EntryProps = {
    action: string;
    entry: AdministrativeEntry;
    onChange: (updated: AdministrativeEntry) => void;
    onDelete?: () => void;
};

export const AdministrativeForm = ({ entry, onChange }: EntryProps) => {
    const { handleSubmit, control } = useForm({ mode: 'onBlur' });

    const onSubmit = (entered: FieldValues) => {
        onChange({
            asOf: entered.asOf,
            comment: entered.comment
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
                            render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                                <DatePickerInput
                                    flexBox
                                    defaultValue={value}
                                    onBlur={onBlur}
                                    onChange={onChange}
                                    name={name}
                                    disableFutureDates
                                    label="Administrative as of"
                                    errorMessage={error?.message}
                                    required
                                />
                            )}
                        />
                    </Grid>
                    <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
                        <Controller
                            control={control}
                            defaultValue={entry.comment}
                            name="comment"
                            rules={{
                                required: { value: true, message: 'Comments are required.' },
                                ...maxLengthRule(2000)
                            }}
                            render={({ field: { onBlur, onChange }, fieldState: { error } }) => (
                                <Grid>
                                    <Grid className="flex-align-self-center">
                                        <Label className="required" htmlFor={'comment'}>
                                            Additional comments:
                                        </Label>
                                    </Grid>
                                    <Grid>
                                        <Textarea
                                            defaultValue={entry.comment ?? undefined}
                                            onBlur={onBlur}
                                            onChange={onChange}
                                            name="comment"
                                            id={'comment'}
                                        />
                                    </Grid>
                                    {error && <ErrorMessage id={`${error}-message`}>{error?.message}</ErrorMessage>}
                                </Grid>
                            )}
                        />
                    </Grid>
                </Grid>
            </section>

            <EntryFooter onSave={handleSubmit(onSubmit)} />
        </>
    );
};
