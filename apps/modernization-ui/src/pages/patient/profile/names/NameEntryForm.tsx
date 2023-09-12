import { Button, Grid, Icon, ModalFooter } from '@trussworks/react-uswds';
import { Controller, FieldValues, useForm } from 'react-hook-form';
import { SelectInput } from 'components/FormInputs/SelectInput';
import { DatePickerInput } from 'components/FormInputs/DatePickerInput';
import { NameEntry } from './NameEntry';
import { usePatientNameCodedValues } from './usePatientNameCodedValues';
import { Input } from 'components/FormInputs/Input';
import { validNameRule } from 'validation/entry';

type EntryProps = {
    action: string;
    entry: NameEntry;
    onChange: (updated: NameEntry) => void;
    onDelete?: () => void;
};

export const NameEntryForm = ({ action, entry, onChange, onDelete }: EntryProps) => {
    const {
        handleSubmit,
        control,
        formState: { isValid }
    } = useForm<NameEntry, Partial<NameEntry>>({ mode: 'onBlur' });

    const coded = usePatientNameCodedValues();

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
                            defaultValue={entry?.asOf}
                            rules={{ required: { value: true, message: 'As of date is required.' } }}
                            render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                                <DatePickerInput
                                    flexBox
                                    onBlur={onBlur}
                                    defaultValue={value}
                                    onChange={onChange}
                                    name={name}
                                    disableFutureDates
                                    htmlFor={name}
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
                            render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                                <SelectInput
                                    flexBox
                                    defaultValue={value}
                                    onBlur={onBlur}
                                    onChange={onChange}
                                    htmlFor={name}
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
                            name="prefix"
                            defaultValue={entry.prefix}
                            render={({ field: { onChange, value, name } }) => (
                                <SelectInput
                                    flexBox
                                    defaultValue={value}
                                    onChange={onChange}
                                    htmlFor={name}
                                    label="Prefix"
                                    options={coded.prefixes}
                                />
                            )}
                        />
                    </Grid>
                    <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
                        <Controller
                            control={control}
                            name="first"
                            defaultValue={entry.first}
                            rules={validNameRule}
                            render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                                <Input
                                    flexBox
                                    onBlur={onBlur}
                                    onChange={onChange}
                                    value={value}
                                    type="text"
                                    label="First name"
                                    name={name}
                                    htmlFor={name}
                                    id={name}
                                    error={error?.message}
                                />
                            )}
                        />
                    </Grid>
                    <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
                        <Controller
                            control={control}
                            name="middle"
                            defaultValue={entry.middle}
                            rules={validNameRule}
                            render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                                <Input
                                    flexBox
                                    onBlur={onBlur}
                                    onChange={onChange}
                                    value={value}
                                    type="text"
                                    label="Middle name"
                                    name={name}
                                    htmlFor={name}
                                    id={name}
                                    error={error?.message}
                                />
                            )}
                        />
                    </Grid>
                    <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
                        <Controller
                            control={control}
                            name="secondMiddle"
                            defaultValue={entry.secondMiddle}
                            rules={validNameRule}
                            render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                                <Input
                                    flexBox
                                    onBlur={onBlur}
                                    onChange={onChange}
                                    value={value}
                                    type="text"
                                    label="Second middle"
                                    name={name}
                                    htmlFor={name}
                                    id={name}
                                    error={error?.message}
                                />
                            )}
                        />
                    </Grid>
                    <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
                        <Controller
                            control={control}
                            name="last"
                            defaultValue={entry.last}
                            rules={validNameRule}
                            render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                                <Input
                                    flexBox
                                    onBlur={onBlur}
                                    onChange={onChange}
                                    value={value}
                                    type="text"
                                    label="Last name"
                                    name={name}
                                    htmlFor={name}
                                    id={name}
                                    error={error?.message}
                                />
                            )}
                        />
                    </Grid>
                    <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
                        <Controller
                            control={control}
                            name="secondLast"
                            defaultValue={entry.secondLast}
                            rules={validNameRule}
                            render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                                <Input
                                    flexBox
                                    onBlur={onBlur}
                                    onChange={onChange}
                                    value={value}
                                    type="text"
                                    label="Second last"
                                    name={name}
                                    htmlFor={name}
                                    id={name}
                                    error={error?.message}
                                />
                            )}
                        />
                    </Grid>
                    <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
                        <Controller
                            control={control}
                            name="suffix"
                            defaultValue={entry.suffix}
                            render={({ field: { onChange, value, name } }) => (
                                <SelectInput
                                    flexBox
                                    defaultValue={value}
                                    onChange={onChange}
                                    htmlFor={name}
                                    label="Suffix"
                                    options={coded.suffixes}
                                />
                            )}
                        />
                    </Grid>
                    <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
                        <Controller
                            control={control}
                            name="degree"
                            defaultValue={entry.degree}
                            render={({ field: { onChange, value, name } }) => (
                                <SelectInput
                                    flexBox
                                    defaultValue={value}
                                    onChange={onChange}
                                    htmlFor={name}
                                    label="Degree"
                                    options={coded.degrees}
                                />
                            )}
                        />
                    </Grid>
                </Grid>
            </div>
            <ModalFooter className="padding-2 margin-left-auto flex-justify display-flex details-footer">
                <Button
                    unstyled
                    className={`text-red display-flex flex-align-center delete--modal-btn ${
                        action !== 'Edit' ? 'ds-u-visibility--hidden' : ''
                    }`}
                    type="button"
                    onClick={onDelete}>
                    <Icon.Delete className="delete-icon" />
                    Delete
                </Button>

                <Button
                    disabled={!isValid}
                    onClick={handleSubmit(onSubmit)}
                    type="submit"
                    className="padding-105 text-center margin-0">
                    Save
                </Button>
            </ModalFooter>
        </div>
    );
};
