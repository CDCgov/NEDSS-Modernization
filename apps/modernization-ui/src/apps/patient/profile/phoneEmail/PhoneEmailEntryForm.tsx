import { ErrorMessage, Grid, Label, Textarea } from '@trussworks/react-uswds';
import { Controller, FieldValues, useForm } from 'react-hook-form';
import { SelectInput } from 'components/FormInputs/SelectInput';
import { DatePickerInput } from 'components/FormInputs/DatePickerInput';
import { Input } from 'components/FormInputs/Input';
import { EntryFooter } from 'apps/patient/profile/entry';
import { usePatientPhoneCodedValues } from './usePatientPhoneCodedValues';
import { PhoneEmailEntry } from './PhoneEmailEntry';
import { validatePhoneNumber } from 'validation/phone';
import { maxLengthRule } from 'validation/entry';

type EntryProps = {
    entry: PhoneEmailEntry;
    onChange: (updated: PhoneEmailEntry) => void;
    onDelete?: () => void;
};

export const PhoneEmailEntryForm = ({ entry, onChange, onDelete }: EntryProps) => {
    const { handleSubmit, control } = useForm({ mode: 'onBlur' });

    const coded = usePatientPhoneCodedValues();

    const onSubmit = (entered: FieldValues) => {
        onChange({
            ...entry,
            ...entered
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
                            defaultValue={entry.type}
                            rules={{ required: { value: true, message: 'Type is required.' } }}
                            render={({ field: { onChange, value }, fieldState: { error } }) => (
                                <SelectInput
                                    flexBox
                                    defaultValue={value}
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
                            name="use"
                            defaultValue={entry.use}
                            rules={{ required: { value: true, message: 'Use is required.' } }}
                            render={({ field: { onChange, value }, fieldState: { error } }) => (
                                <SelectInput
                                    flexBox
                                    defaultValue={value}
                                    onChange={onChange}
                                    htmlFor={'use'}
                                    label="Use"
                                    options={coded.uses}
                                    error={error?.message}
                                    required
                                />
                            )}
                        />
                    </Grid>
                    <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
                        <Controller
                            control={control}
                            name="countryCode"
                            defaultValue={entry.countryCode}
                            rules={{
                                pattern: {
                                    value: /^\+?\d{1,3}$/,
                                    message: 'A country code should be 1 to 3 digits'
                                }
                            }}
                            render={({ field: { onChange, value, onBlur }, fieldState: { error } }) => {
                                return (
                                    <Input
                                        flexBox
                                        onChange={onChange}
                                        onBlur={onBlur}
                                        defaultValue={value}
                                        type="tel"
                                        label="Country code"
                                        name="countryCode"
                                        htmlFor="countryCode"
                                        id="countryCode"
                                        error={error?.message}
                                    />
                                );
                            }}
                        />
                    </Grid>
                    <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
                        <Controller
                            control={control}
                            name="number"
                            defaultValue={entry.number}
                            rules={{
                                validate: {
                                    properNumber: (value) => validatePhoneNumber(value)
                                }
                            }}
                            render={({ field: { onChange, onBlur, value }, fieldState: { error } }) => (
                                <Input
                                    flexBox
                                    onBlur={onBlur}
                                    onChange={onChange}
                                    defaultValue={value}
                                    type="text"
                                    label="Phone number"
                                    name="number"
                                    htmlFor="number"
                                    id="number"
                                    mask="___-___-____"
                                    pattern="\d{3}-\d{3}-\d{4}"
                                    error={
                                        error &&
                                        'Please enter a valid phone number (XXX-XXX-XXXX) using only numeric characters (0-9).'
                                    }
                                />
                            )}
                        />
                    </Grid>
                    <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
                        <Controller
                            control={control}
                            name="extension"
                            defaultValue={entry.extension}
                            rules={{
                                pattern: {
                                    value: /^\+?\d{1,4}$/,
                                    message: 'A Extension should be 1 to 4 digits'
                                }
                            }}
                            render={({ field: { onChange, onBlur, value }, fieldState: { error } }) => (
                                <Input
                                    flexBox
                                    onBlur={onBlur}
                                    onChange={onChange}
                                    defaultValue={value}
                                    type="text"
                                    label="Extension"
                                    name="extension"
                                    htmlFor="extension"
                                    id="extension"
                                    error={error && 'Invalid extension'}
                                />
                            )}
                        />
                    </Grid>
                    <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
                        <Controller
                            control={control}
                            name="email"
                            defaultValue={entry.email}
                            rules={{
                                pattern: {
                                    value: /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/,
                                    message: 'Please enter a valid email address (example: youremail@website.com)'
                                },
                                ...maxLengthRule(100)
                            }}
                            render={({ field: { onChange, onBlur, value }, fieldState: { error } }) => (
                                <Input
                                    flexBox
                                    onBlur={onBlur}
                                    onChange={onChange}
                                    defaultValue={value}
                                    type="text"
                                    label="Email address"
                                    name="email"
                                    htmlFor="email"
                                    id="email"
                                    error={error?.message}
                                />
                            )}
                        />
                    </Grid>
                    <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
                        <Controller
                            control={control}
                            name="url"
                            rules={maxLengthRule(100)}
                            defaultValue={entry.url}
                            render={({ field: { onChange, onBlur, value }, fieldState: { error } }) => (
                                <Input
                                    flexBox
                                    onBlur={onBlur}
                                    onChange={onChange}
                                    defaultValue={value}
                                    type="text"
                                    label="Url"
                                    name="url"
                                    htmlFor="url"
                                    id="url"
                                    error={error?.message}
                                />
                            )}
                        />
                    </Grid>
                    <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
                        <Controller
                            control={control}
                            name="comment"
                            defaultValue={entry.comment}
                            rules={maxLengthRule(2000)}
                            render={({ field: { onChange, onBlur, value }, fieldState: { error } }) => (
                                <Grid>
                                    <Grid className="flex-align-self-center">
                                        <Label htmlFor={'comment'}>Additional comments:</Label>
                                    </Grid>
                                    <Grid>
                                        <Textarea
                                            onBlur={onBlur}
                                            onChange={onChange}
                                            name="comment"
                                            id="comment"
                                            defaultValue={value}
                                        />
                                    </Grid>
                                    {error && <ErrorMessage id={`${error}-message`}>{error?.message}</ErrorMessage>}
                                </Grid>
                            )}
                        />
                    </Grid>
                </Grid>
            </section>

            <EntryFooter onSave={handleSubmit(onSubmit)} onDelete={onDelete} />
        </>
    );
};
