import { Button, ButtonGroup, Grid, Label, ModalFooter, Textarea } from '@trussworks/react-uswds';
import { Controller, FieldValues, useForm } from 'react-hook-form';
import { DatePickerInput } from '../../../../components/FormInputs/DatePickerInput';
import { SelectInput } from '../../../../components/FormInputs/SelectInput';
import { Input } from '../../../../components/FormInputs/Input';
import { SearchCriteriaContext } from '../../../../providers/SearchCriteriaContext';
import { IdentificationEntry } from './identification';

type EntryProps = {
    action: string;
    entry: IdentificationEntry;
    onChange: (updated: IdentificationEntry) => void;
    onCancel: () => void;
};

export const IdentificationEntryForm = ({ action, entry, onChange, onCancel }: EntryProps) => {
    const methods = useForm({ mode: 'onBlur' });
    const { handleSubmit, control } = methods;

    const onSubmit = (entered: FieldValues) => {
        const obj: IdentificationEntry = {
            patient: entry.patient,
            asOf: entered.asOf,
            type: entered?.type,
            value: entered?.id,
            state: entered?.state,
            comment: entered?.additionalComments
        };
        entry.sequence && (obj.sequence = entry.sequence);
        onChange(obj);
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
                        <SearchCriteriaContext.Consumer>
                            {({ searchCriteria }) => (
                                <Controller
                                    control={control}
                                    name="type"
                                    defaultValue={entry?.type}
                                    rules={{ required: { value: true, message: 'Type is required.' } }}
                                    render={({ field: { onChange, value }, fieldState: { error } }) => (
                                        <SelectInput
                                            flexBox
                                            defaultValue={value}
                                            onChange={onChange}
                                            htmlFor={'type'}
                                            label="Type"
                                            options={
                                                searchCriteria?.identificationType
                                                    ? searchCriteria.identificationType.map((identification) => {
                                                          return {
                                                              name: identification?.codeShortDescTxt!,
                                                              value: identification?.id?.code!
                                                          };
                                                      })
                                                    : []
                                            }
                                            error={error?.message}
                                        />
                                    )}
                                />
                            )}
                        </SearchCriteriaContext.Consumer>
                    </Grid>
                    <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
                        <Controller
                            control={control}
                            name="id"
                            defaultValue={entry?.value}
                            render={({ field: { onChange, value } }) => (
                                <Input
                                    flexBox
                                    onChange={onChange}
                                    defaultValue={value}
                                    type="text"
                                    label="ID #"
                                    name="id"
                                    htmlFor="id"
                                    id="id"
                                />
                            )}
                        />
                    </Grid>
                    <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
                        <SearchCriteriaContext.Consumer>
                            {({ searchCriteria }) => (
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
                                            options={
                                                searchCriteria?.states
                                                    ? searchCriteria.states.map((states) => {
                                                          return {
                                                              name: states?.codeDescTxt!,
                                                              value: states?.stateNm!
                                                          };
                                                      })
                                                    : []
                                            }
                                        />
                                    )}
                                />
                            )}
                        </SearchCriteriaContext.Consumer>
                    </Grid>
                    <Grid col={12} className="border-bottom border-base-lighter padding-bottom-2 padding-2">
                        <Controller
                            control={control}
                            name="additionalComments"
                            defaultValue={entry?.comment}
                            render={({ field: { onChange } }) => (
                                <Grid row>
                                    <Grid col={6} className="flex-align-self-center">
                                        <Label htmlFor={'additionalComments'}>Additional comments:</Label>
                                    </Grid>
                                    <Grid col={6}>
                                        <Textarea
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
