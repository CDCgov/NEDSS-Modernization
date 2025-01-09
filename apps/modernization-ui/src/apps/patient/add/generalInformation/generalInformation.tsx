import { Grid } from '@trussworks/react-uswds';
import { Controller, useFormContext } from 'react-hook-form';
import { DatePickerInput, validDateRule } from 'design-system/date';
import { Input } from 'components/FormInputs/Input';
import FormCard from 'components/FormCard/FormCard';
import { maxLengthRule, validateRequiredRule } from 'validation/entry';

const AS_OF_DATE_LABEL = 'Information as of date';

export default function GeneralInformation({ id, title }: { id?: string; title?: string }) {
    const { control } = useFormContext();
    return (
        <FormCard id={id} title={title}>
            <Grid col={12} className="padding-x-3 padding-bottom-3">
                <Grid row>
                    <Grid col={12} className="margin-top-2">
                        <span>All fields marked with</span> <span className="text-red">*</span> are required
                    </Grid>
                    <Grid col={6}>
                        <Controller
                            control={control}
                            name="asOf"
                            rules={{ ...validDateRule(AS_OF_DATE_LABEL), ...validateRequiredRule(AS_OF_DATE_LABEL) }}
                            render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                                <DatePickerInput
                                    id={name}
                                    label={AS_OF_DATE_LABEL}
                                    value={value}
                                    onBlur={onBlur}
                                    onChange={onChange}
                                    name={name}
                                    error={error?.message}
                                    required
                                />
                            )}
                        />
                    </Grid>
                </Grid>
                <Grid row>
                    <Grid col={6}>
                        <Controller
                            control={control}
                            name="comment"
                            rules={maxLengthRule(2000)}
                            render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                                <Input
                                    label="Comments"
                                    type="text"
                                    onBlur={onBlur}
                                    onChange={onChange}
                                    defaultValue={value}
                                    name={name}
                                    htmlFor={name}
                                    id={name}
                                    error={error?.message}
                                    multiline
                                />
                            )}
                        />
                    </Grid>
                </Grid>
            </Grid>
        </FormCard>
    );
}
