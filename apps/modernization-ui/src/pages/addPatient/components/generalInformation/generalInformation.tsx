import { Grid, Label, Textarea } from '@trussworks/react-uswds';
import FormCard from 'components/FormCard/FormCard';
import { Controller, useFormContext } from 'react-hook-form';
import { DatePickerInput } from 'components/FormInputs/DatePickerInput';

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
                        <Label className="required" htmlFor="asOf">
                            <span data-testid="date-lable">Information as of Date</span>{' '}
                        </Label>
                        <Controller
                            rules={{
                                required: { value: true, message: 'This field is required' }
                            }}
                            control={control}
                            name="asOf"
                            render={({ field: { onChange, value, name }, fieldState: { error } }) => (
                                <DatePickerInput
                                    defaultValue={value}
                                    onChange={onChange}
                                    name={name}
                                    htmlFor={name}
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
                            render={({ field: { onChange, name } }) => (
                                <>
                                    <Label htmlFor={name}>Comments</Label>
                                    <Textarea onChange={onChange} name={name} id={name} />
                                </>
                            )}
                        />
                    </Grid>
                </Grid>
            </Grid>
        </FormCard>
    );
}
