import { ErrorMessage, Grid, Label, Textarea } from '@trussworks/react-uswds';
import FormCard from 'components/FormCard/FormCard';
import { Controller, useFormContext } from 'react-hook-form';
import { DatePickerInput } from 'components/FormInputs/DatePickerInput';
import { maxLengthRule } from 'validation/entry';

export default function GeneralInformation({ id, title }: { id?: string; title?: string }) {
    const { control } = useFormContext();
    return (
        <FormCard id={id} title={title}>
            <Grid col={12} className="padding-x-3 padding-bottom-3">
                <Grid row>
                    <Grid col={12} className="margin-top-2">
                        <span data-testid="required-text">All fields marked with</span>{' '}
                        <span className="text-red">*</span> are required
                    </Grid>
                    <Grid col={6}>
                        <Controller
                            rules={{
                                required: { value: true, message: 'This field is required' }
                            }}
                            control={control}
                            name="asOf"
                            render={({ field: { onChange, value, name }, fieldState: { error } }) => (
                                <DatePickerInput
                                    defaultValue={value}
                                    label="Information as of Date"
                                    onChange={onChange}
                                    name={name}
                                    disableFutureDates
                                    errorMessage={error?.message}
                                />
                            )}
                        />
                    </Grid>
                </Grid>
                <Grid row>
                    <Grid col={6}>
                        <Controller
                            control={control}
                            name="comments"
                            rules={maxLengthRule(2000)}
                            render={({ field: { onChange, name, onBlur, value }, fieldState: { error } }) => (
                                <>
                                    <Label htmlFor={name}>Comments</Label>
                                    <Textarea
                                        defaultValue={value}
                                        onChange={onChange}
                                        onBlur={onBlur}
                                        name={name}
                                        id={name}
                                    />
                                    {error?.message && (
                                        <ErrorMessage id={error?.message}>{error?.message}</ErrorMessage>
                                    )}
                                </>
                            )}
                        />
                    </Grid>
                </Grid>
            </Grid>
        </FormCard>
    );
}
