import { ErrorMessage, Grid, Label, Textarea } from '@trussworks/react-uswds';
import FormCard from 'components/FormCard/FormCard';
import { Controller, useFormContext } from 'react-hook-form';
import { DatePickerInput } from 'components/FormInputs/DatePickerInput';
import { maxLengthRule } from 'validation/entry';
import classNames from 'classnames';
import styles from '../extended.module.scss';

export default function Administrative({ id, title }: { id?: string; title?: string }) {
    const { control } = useFormContext();
    return (
        <FormCard id={id} title={title}>
            <Grid col={12} className="padding-3">
                <Grid row className={styles.flexBox}>
                    <Grid col={4}>
                        <Label className={classNames({ required: true })} htmlFor={'asOf'}>
                            Administrative Information as of
                        </Label>
                    </Grid>
                    <Grid col={4}>
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
                                    disableFutureDates
                                    errorMessage={error?.message}
                                />
                            )}
                        />
                    </Grid>
                </Grid>
                <Grid row className={styles.flexBox}>
                    <Grid col={4}>
                        <Label htmlFor={'comments'}>General Comments</Label>
                    </Grid>
                    <Grid col={4}>
                        <Controller
                            control={control}
                            name="comments"
                            rules={maxLengthRule(2000)}
                            render={({ field: { onChange, name, onBlur }, fieldState: { error } }) => (
                                <>
                                    <Textarea onChange={onChange} onBlur={onBlur} name={name} id={name} />
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
