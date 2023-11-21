import { useEffect } from 'react';
import { Controller, useForm } from 'react-hook-form';
import { Accordion, Button, Checkbox, ErrorMessage, Form, FormGroup, Grid, Label } from '@trussworks/react-uswds';
import { AccordionItemProps } from '@trussworks/react-uswds/lib/components/Accordion/Accordion';
import { PersonFilter, RecordStatus } from 'generated/graphql/schema';
import { DatePickerInput } from 'components/FormInputs/DatePickerInput';
import { Input } from 'components/FormInputs/Input';
import { SelectInput } from 'components/FormInputs/SelectInput';
import { objectOrUndefined } from 'utils/objectOrUndefined';
import { validNameRule } from 'validation/entry';
import { AddressForm } from './AddressForm';
import { ContactForm } from './ContactForm';
import { EthnicityForm } from './EthnicityForm';
import { IDForm } from './IdForm';

type PatientSearchProps = {
    handleSubmission: (data: PersonFilter) => void;
    personFilter: PersonFilter | undefined;
    clearAll: () => void;
};

export const PatientSearch = ({ handleSubmission, personFilter, clearAll }: PatientSearchProps) => {
    const form = useForm<PersonFilter>({ defaultValues: { recordStatus: [RecordStatus.Active] }, mode: 'onBlur' });
    useEffect(() => {
        if (personFilter) {
            form.reset({ ...personFilter }, { keepDefaultValues: true });
        }
    }, [personFilter]);

    useEffect(() => {}, [form.formState.errors]);

    const handleRecordStatusChange = (
        value: RecordStatus[],
        status: RecordStatus,
        isChecked: boolean,
        onChange: (recordStatus: RecordStatus[]) => void
    ): void => {
        if (isChecked) {
            value.push(status);
            onChange(value);
        } else {
            onChange(value.filter((s) => s !== status));
        }
        form.trigger('recordStatus'); // Trigger validation
    };

    const handleSubmit = (filter: PersonFilter) => {
        // Clean up any empty filter objects
        Object.values(filter.identification ?? {}).length > 0;
        filter.identification = objectOrUndefined(filter.identification);
        handleSubmission(filter);
    };

    const simpleSearchItems: AccordionItemProps[] = [
        {
            title: 'Basic information',
            content: (
                <>
                    <Grid col={12}>
                        <Controller
                            control={form.control}
                            name="lastName"
                            rules={validNameRule}
                            render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                                <Input
                                    onBlur={onBlur}
                                    onChange={onChange}
                                    type="text"
                                    label="Last name"
                                    name={name}
                                    defaultValue={value}
                                    htmlFor={name}
                                    id={name}
                                    error={error?.message}
                                />
                            )}
                        />
                    </Grid>
                    <Grid col={12}>
                        <Controller
                            control={form.control}
                            name="firstName"
                            rules={validNameRule}
                            render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                                <Input
                                    onBlur={onBlur}
                                    onChange={onChange}
                                    defaultValue={value}
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
                    <Grid col={12}>
                        <Controller
                            control={form.control}
                            name="dateOfBirth"
                            render={({ field: { onChange, value, name } }) => (
                                <DatePickerInput
                                    defaultValue={value}
                                    onChange={onChange}
                                    name={name}
                                    htmlFor={name}
                                    disableFutureDates
                                    label="Date of birth"
                                    id={name}
                                />
                            )}
                        />
                    </Grid>
                    <Grid col={12}>
                        <Controller
                            control={form.control}
                            name="gender"
                            render={({ field: { onChange, value, name } }) => (
                                <SelectInput
                                    defaultValue={value}
                                    onChange={onChange}
                                    name={name}
                                    htmlFor={name}
                                    label="Sex"
                                    id={name}
                                    options={[
                                        { name: 'Male', value: 'M' },
                                        { name: 'Female', value: 'F' },
                                        { name: 'Other', value: 'U' }
                                    ]}
                                />
                            )}
                        />
                    </Grid>
                    <Grid col={12}>
                        <Controller
                            control={form.control}
                            name="id"
                            render={({ field: { onChange, value, name } }) => (
                                <Input
                                    onChange={onChange}
                                    defaultValue={value}
                                    type="text"
                                    label="Patient ID"
                                    name={name}
                                    htmlFor={name}
                                    id={name}
                                />
                            )}
                        />
                    </Grid>
                    <Grid col={12}>
                        <FormGroup error={!!form.formState.errors.recordStatus}>
                            <Label htmlFor={''}>Include records that are</Label>
                            {form.formState.errors.recordStatus ? (
                                <ErrorMessage id="record-status-error-message">
                                    At least one status is required
                                </ErrorMessage>
                            ) : null}
                            <Controller
                                control={form.control}
                                name="recordStatus"
                                rules={{ validate: (v) => v.length !== 0 }}
                                render={({ field: { onChange, value } }) => {
                                    return (
                                        <>
                                            <Grid row>
                                                <Grid col={6}>
                                                    <Checkbox
                                                        id={'record-status-active'}
                                                        onChange={(v) =>
                                                            handleRecordStatusChange(
                                                                value,
                                                                RecordStatus.Active,
                                                                v.target.checked,
                                                                onChange
                                                            )
                                                        }
                                                        name={'name'}
                                                        label={'Active'}
                                                        checked={form
                                                            .getValues('recordStatus')
                                                            .includes(RecordStatus.Active)}
                                                    />
                                                </Grid>
                                                <Grid col={6}>
                                                    <Checkbox
                                                        id={'record-status-deleted'}
                                                        onChange={(v) =>
                                                            handleRecordStatusChange(
                                                                value,
                                                                RecordStatus.LogDel,
                                                                v.target.checked,
                                                                onChange
                                                            )
                                                        }
                                                        name={'name'}
                                                        label={'Deleted'}
                                                        checked={form
                                                            .getValues('recordStatus')
                                                            .includes(RecordStatus.LogDel)}
                                                    />
                                                </Grid>
                                                <Grid col={6}>
                                                    <Checkbox
                                                        id={'record-status-superceded'}
                                                        onChange={(v) =>
                                                            handleRecordStatusChange(
                                                                value,
                                                                RecordStatus.Superceded,
                                                                v.target.checked,
                                                                onChange
                                                            )
                                                        }
                                                        name={'name'}
                                                        label={'Superseded'}
                                                        checked={form
                                                            .getValues('recordStatus')
                                                            .includes(RecordStatus.Superceded)}
                                                    />
                                                </Grid>
                                            </Grid>
                                        </>
                                    );
                                }}
                            />
                        </FormGroup>
                    </Grid>
                </>
            ),
            expanded: true,
            id: '1',
            headingLevel: 'h4',
            className: 'accordian-item'
        },
        {
            title: 'Address',
            content: <AddressForm control={form.control} />,
            expanded: false,
            id: '2',
            headingLevel: 'h4',
            className: 'accordian-item'
        },
        {
            title: 'Contact',
            content: <ContactForm control={form.control} errors={form.formState.errors} />,
            expanded: false,
            id: '3',
            headingLevel: 'h4',
            className: 'accordian-item'
        },
        {
            title: 'ID',
            content: <IDForm control={form} />,
            expanded: false,
            id: '4',
            headingLevel: 'h4',
            className: 'accordian-item'
        },
        {
            title: 'Race / Ethnicity',
            content: <EthnicityForm control={form.control} />,
            expanded: false,
            id: '5',
            headingLevel: 'h4',
            className: 'accordian-item'
        }
    ];

    return (
        <Form onSubmit={form.handleSubmit(handleSubmit)} className="width-full maxw-full">
            <div style={{ height: `calc(100vh - 405px)`, overflowY: 'auto' }}>
                <Accordion items={simpleSearchItems} multiselectable={true} />
            </div>
            <Grid row className="bottom-search">
                <Grid col={12} className="padding-x-2">
                    <Button disabled={!form.formState.isValid} className="width-full clear-btn" type={'submit'}>
                        Search
                    </Button>
                </Grid>
                <Grid col={12} className="padding-x-2">
                    <Button
                        className="width-full clear-btn"
                        type={'button'}
                        onClick={() => {
                            form.reset({}, { keepDefaultValues: true });
                            clearAll();
                        }}
                        outline>
                        Clear all
                    </Button>
                </Grid>
            </Grid>
        </Form>
    );
};
