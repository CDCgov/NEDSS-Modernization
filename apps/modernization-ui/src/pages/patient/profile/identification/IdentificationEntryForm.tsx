import { Button, ButtonGroup, Grid, ModalFooter } from '@trussworks/react-uswds';
import { Controller, FieldValues, useForm } from 'react-hook-form';
import { DatePickerInput } from 'components/FormInputs/DatePickerInput';
import { SelectInput } from 'components/FormInputs/SelectInput';
import { Input } from 'components/FormInputs/Input';
import { IdentificationEntry } from './identification';
import { usePatientIdentificationCodedValues } from './usePatientIdentificationCodedValues';
import { externalizeDateTime } from 'date';
import { orNull } from 'utils';

type Props = {
    action: string;
    entry: IdentificationEntry;
    onChange: (updated: IdentificationEntry) => void;
    onCancel: () => void;
};

export const IdentificationEntryForm = ({ action, entry, onChange, onCancel }: Props) => {
    const {
        handleSubmit,
        control,
        formState: { isValid }
    } = useForm({ mode: 'onBlur' });

    const coded = usePatientIdentificationCodedValues();

    const onSubmit = (entered: FieldValues) => {
        onChange({
            patient: entry.patient,
            sequence: entry.sequence,
            asOf: externalizeDateTime(entered.asOf),
            type: orNull(entered.type),
            value: orNull(entered.id),
            state: orNull(entered.state)
        });
    };

    return (
        <div onSubmit={onSubmit} className="width-full maxw-full modal-form">
            <div className="modal-body">
                <Grid row>
                    <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
                        <Controller
                            control={control}
                            name="asOf"
                            defaultValue={entry?.asOf}
                            rules={{ required: { value: true, message: 'As of date is required.' } }}
                            render={({ field: { onBlur, onChange, value }, fieldState: { error } }) => (
                                <DatePickerInput
                                    flexBox
                                    onBlur={onBlur}
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
                            defaultValue={entry?.type}
                            rules={{ required: { value: true, message: 'Type is required.' } }}
                            render={({ field: { onBlur, onChange, value }, fieldState: { error } }) => (
                                <SelectInput
                                    flexBox
                                    defaultValue={value}
                                    onBlur={onBlur}
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
                            name="id"
                            defaultValue={entry.value}
                            rules={{ required: { value: true, message: 'ID # is required.' } }}
                            render={({ field: { onBlur, onChange, value }, fieldState: { error } }) => (
                                <Input
                                    flexBox
                                    onBlur={onBlur}
                                    onChange={onChange}
                                    defaultValue={value}
                                    type="text"
                                    label="ID #"
                                    name="id"
                                    htmlFor="id"
                                    id="id"
                                    error={error?.message}
                                />
                            )}
                        />
                    </Grid>
                    <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
                        <Controller
                            control={control}
                            name="state"
                            defaultValue={entry?.state}
                            render={({ field: { onChange, value } }) => (
                                <SelectInput
                                    flexBox
                                    defaultValue={value}
                                    onChange={onChange}
                                    htmlFor={'state'}
                                    label="Issued state"
                                    options={coded.authorities}
                                />
                            )}
                        />
                    </Grid>
                </Grid>
            </div>

            <ModalFooter className="border-top border-base-lighter padding-2 margin-left-auto margin-0">
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
            </ModalFooter>
        </div>
    );
};
