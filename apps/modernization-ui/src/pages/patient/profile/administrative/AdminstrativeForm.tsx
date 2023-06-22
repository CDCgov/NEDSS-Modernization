import { Button, ButtonGroup, Grid, Label, ModalFooter, Textarea } from '@trussworks/react-uswds';
import { Controller, FieldValues, useForm } from 'react-hook-form';
import { DatePickerInput } from '../../../../components/FormInputs/DatePickerInput';
import { AdministrativeEntry } from './administrative';

type EntryProps = {
    action: string;
    entry: AdministrativeEntry;
    onChange: (updated: AdministrativeEntry) => void;
    onCancel: () => void;
};

export const AdministrativeForm = ({ action, entry, onChange, onCancel }: EntryProps) => {
    const methods = useForm();
    const { handleSubmit, control } = methods;

    const onSubmit = (entered: FieldValues) => {
        onChange({
            patient: entry.patient,
            asOf: entry.asOf,
            comment: entered?.additionalComments
        });
    };

    return (
        <div onSubmit={onSubmit} className="width-full maxw-full modal-form">
            <div className="modal-body">
                <Grid row>
                    <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
                        <Controller
                            control={control}
                            name="administrativeDate"
                            defaultValue={entry.asOf}
                            render={({ field: { onChange, value } }) => (
                                <DatePickerInput
                                    flexBox
                                    defaultValue={value}
                                    onChange={onChange}
                                    name="administrativeDate"
                                    htmlFor={'administrativeDate'}
                                    label="Administrative as of"
                                />
                            )}
                        />
                    </Grid>
                    <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
                        <Controller
                            control={control}
                            defaultValue={entry?.comment}
                            name="additionalComments"
                            render={({ field: { onChange } }) => (
                                <Grid row>
                                    <Grid col={6} className="flex-align-self-center">
                                        <Label htmlFor={'additionalComments'}>Additional comments:</Label>
                                    </Grid>
                                    <Grid col={6}>
                                        <Textarea
                                            defaultValue={entry?.comment || ''}
                                            onChange={onChange}
                                            name="additionalComments"
                                            id={'additionalComments'}
                                        />
                                    </Grid>
                                </Grid>
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
