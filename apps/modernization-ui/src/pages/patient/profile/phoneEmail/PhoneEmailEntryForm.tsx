import { Button, ButtonGroup, Grid, Label, Textarea } from '@trussworks/react-uswds';
import { Controller, FieldValues, useForm } from 'react-hook-form';
import { SelectInput } from 'components/FormInputs/SelectInput';
import { DatePickerInput } from 'components/FormInputs/DatePickerInput';
import { Input } from 'components/FormInputs/Input';
import { usePatientPhoneCodedValues } from './usePatientPhoneCodedValues';
import { PhoneEmailEntry } from './PhoneEmailEntry';

type EntryProps = {
    action: string;
    entry: PhoneEmailEntry;
    onChange: (updated: PhoneEmailEntry) => void;
    onCancel: () => void;
};

export const PhoneEmailEntryForm = ({ action, entry, onChange, onCancel }: EntryProps) => {
    const { handleSubmit, control } = useForm();

    const coded = usePatientPhoneCodedValues();

    const onSubmit = (entered: FieldValues) => {
        onChange({
            ...entry,
            ...entered
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
                            render={({ field: { onChange, value }, fieldState: { error } }) => (
                                <DatePickerInput
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
                                />
                            )}
                        />
                    </Grid>
                    <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
                        <Controller
                            control={control}
                            name="countryCode"
                            defaultValue={entry.countryCode}
                            render={({ field: { onChange, value } }) => (
                                <Input
                                    flexBox
                                    onChange={onChange}
                                    defaultValue={value}
                                    type="text"
                                    label="Country code"
                                    name="countryCode"
                                    htmlFor="countryCode"
                                    id="countryCode"
                                />
                            )}
                        />
                    </Grid>
                    <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
                        <Controller
                            control={control}
                            name="number"
                            defaultValue={entry.number}
                            render={({ field: { onChange, value } }) => (
                                <Input
                                    flexBox
                                    onChange={onChange}
                                    defaultValue={value}
                                    type="text"
                                    label="Phone number"
                                    name="number"
                                    htmlFor="number"
                                    id="number"
                                />
                            )}
                        />
                    </Grid>
                    <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
                        <Controller
                            control={control}
                            name="extension"
                            defaultValue={entry.extension}
                            render={({ field: { onChange, value } }) => (
                                <Input
                                    flexBox
                                    onChange={onChange}
                                    defaultValue={value}
                                    type="text"
                                    label="Extension"
                                    name="extension"
                                    htmlFor="extension"
                                    id="extension"
                                />
                            )}
                        />
                    </Grid>
                    <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
                        <Controller
                            control={control}
                            name="email"
                            defaultValue={entry.email}
                            render={({ field: { onChange, value } }) => (
                                <Input
                                    flexBox
                                    onChange={onChange}
                                    defaultValue={value}
                                    type="text"
                                    label="Email address"
                                    name="email"
                                    htmlFor="email"
                                    id="email"
                                />
                            )}
                        />
                    </Grid>
                    <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
                        <Controller
                            control={control}
                            name="url"
                            defaultValue={entry.url}
                            render={({ field: { onChange, value } }) => (
                                <Input
                                    flexBox
                                    onChange={onChange}
                                    defaultValue={value}
                                    type="text"
                                    label="Url"
                                    name="url"
                                    htmlFor="url"
                                    id="url"
                                />
                            )}
                        />
                    </Grid>
                    <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
                        <Controller
                            control={control}
                            name="comment"
                            defaultValue={entry.comment}
                            render={({ field: { onChange, value } }) => (
                                <Grid>
                                    <Grid className="flex-align-self-center">
                                        <Label htmlFor={'comment'}>Additional comments:</Label>
                                    </Grid>
                                    <Grid>
                                        <Textarea
                                            onChange={onChange}
                                            name="comment"
                                            id="comment"
                                            defaultValue={value}
                                        />
                                    </Grid>
                                </Grid>
                            )}
                        />
                    </Grid>
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
