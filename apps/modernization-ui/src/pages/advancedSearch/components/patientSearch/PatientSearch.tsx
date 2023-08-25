import { Accordion, Button, Checkbox, ErrorMessage, Form, FormGroup, Grid, Label } from '@trussworks/react-uswds';
import { AccordionItemProps } from '@trussworks/react-uswds/lib/components/Accordion/Accordion';
import { useEffect, useState } from 'react';
import { Controller, useForm } from 'react-hook-form';
import { Gender, PersonFilter, RecordStatus } from '../../../../generated/graphql/schema';
import { DatePickerInput } from '../../../../components/FormInputs/DatePickerInput';
import { Input } from '../../../../components/FormInputs/Input';
import { SelectInput } from '../../../../components/FormInputs/SelectInput';
import { AddressForm } from './AddressForm';
import { ContactForm } from './ContactForm';
import { EthnicityForm } from './EthnicityForm';
import { IDForm } from './IdForm';
import { validate as validatePhoneNumber } from 'validation/phone/search';
import { validateZipCode } from '../../../../utils/ZipValidation';

type PatientSearchProps = {
    handleSubmission: (data: PersonFilter) => void;
    data: PersonFilter | undefined;
    clearAll: () => void;
};

export const PatientSearch = ({ handleSubmission, data, clearAll }: PatientSearchProps) => {
    const [selectedRecordStatus, setSelectedRecordStatus] = useState([] as RecordStatus[]);
    const methods = useForm();
    const {
        handleSubmit,
        control,
        formState: { errors },
        reset
    } = methods;

    useEffect(() => {
        if (data) {
            methods.reset({
                firstName: data.firstName,
                lastName: data.lastName,
                address: data.address,
                city: data.city,
                state: data.state,
                zip: data.zip,
                patientId: data.id,
                dob: data.dateOfBirth,
                gender: data.gender,
                phoneNumber: data.phoneNumber,
                email: data.email,
                identificationNumber: data.identification?.identificationNumber,
                identificationType: data.identification?.identificationType,
                ethnicity: data.ethnicity,
                race: data.race
            });
        }
        // Default to Active checked
        data?.recordStatus
            ? setSelectedRecordStatus(data.recordStatus)
            : setSelectedRecordStatus([RecordStatus.Active]);
    }, [data]);

    useEffect(() => {}, [errors]);

    const simpleSearchItems: AccordionItemProps[] = [
        {
            title: 'Basic information',
            content: (
                <>
                    <Grid col={12}>
                        <Controller
                            control={control}
                            name="lastName"
                            render={({ field: { onChange, value, name } }) => (
                                <Input
                                    onChange={onChange}
                                    type="text"
                                    label="Last name"
                                    name={name}
                                    defaultValue={value}
                                    htmlFor={name}
                                    id={name}
                                    error={errors?.lastName && 'Last name is required.'}
                                />
                            )}
                        />
                    </Grid>
                    <Grid col={12}>
                        <Controller
                            control={control}
                            name="firstName"
                            render={({ field: { onChange, value, name } }) => (
                                <Input
                                    onChange={onChange}
                                    defaultValue={value}
                                    type="text"
                                    label="First name"
                                    name={name}
                                    htmlFor={name}
                                    id={name}
                                    error={errors?.firstName && 'First name is required.'}
                                />
                            )}
                        />
                    </Grid>
                    <Grid col={12}>
                        <Controller
                            control={control}
                            name="dob"
                            render={({ field: { onChange, value, name } }) => (
                                <DatePickerInput
                                    defaultValue={value}
                                    onChange={onChange}
                                    name={name}
                                    htmlFor={name}
                                    label="Date of birth"
                                    id={name}
                                />
                            )}
                        />
                    </Grid>
                    <Grid col={12}>
                        <Controller
                            control={control}
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
                                        { name: 'Male', value: Gender.M },
                                        { name: 'Female', value: Gender.F },
                                        { name: 'Other', value: Gender.U }
                                    ]}
                                />
                            )}
                        />
                    </Grid>
                    <Grid col={12}>
                        <Controller
                            control={control}
                            name="patientId"
                            render={({ field: { onChange, value, name } }) => (
                                <Input
                                    onChange={onChange}
                                    defaultValue={value}
                                    type="text"
                                    label="Patient Id"
                                    name={name}
                                    htmlFor={name}
                                    id={name}
                                />
                            )}
                        />
                    </Grid>
                    <Grid col={12}>
                        <FormGroup error={selectedRecordStatus.length === 0}>
                            <Label htmlFor={''}>Include records that are</Label>
                            {selectedRecordStatus.length === 0 && (
                                <ErrorMessage id="record-status-error-message">
                                    At least one status is required
                                </ErrorMessage>
                            )}
                            <Grid row>
                                <Grid col={6}>
                                    <Checkbox
                                        id={'record-status-active'}
                                        onChange={(v) =>
                                            handleRecordStatusChange(RecordStatus.Active, v.target.checked)
                                        }
                                        name={'name'}
                                        label={'Active'}
                                        checked={selectedRecordStatus.includes(RecordStatus.Active)}
                                    />
                                </Grid>
                                <Grid col={6}>
                                    <Checkbox
                                        id={'record-status-deleted'}
                                        onChange={(v) =>
                                            handleRecordStatusChange(RecordStatus.LogDel, v.target.checked)
                                        }
                                        name={'name'}
                                        label={'Deleted'}
                                        checked={selectedRecordStatus.includes(RecordStatus.LogDel)}
                                    />
                                </Grid>
                                <Grid col={6}>
                                    <Checkbox
                                        id={'record-status-superceded'}
                                        onChange={(v) =>
                                            handleRecordStatusChange(RecordStatus.Superceded, v.target.checked)
                                        }
                                        name={'name'}
                                        label={'Superseded'}
                                        checked={selectedRecordStatus.includes(RecordStatus.Superceded)}
                                    />
                                </Grid>
                            </Grid>
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
            content: <AddressForm control={control} errors={errors} />,
            expanded: false,
            id: '2',
            headingLevel: 'h4',
            className: 'accordian-item'
        },
        {
            title: 'Contact',
            content: <ContactForm control={control} errors={errors} />,
            expanded: false,
            id: '3',
            headingLevel: 'h4',
            className: 'accordian-item'
        },
        {
            title: 'ID',
            content: <IDForm control={control} />,
            expanded: false,
            id: '4',
            headingLevel: 'h4',
            className: 'accordian-item'
        },
        {
            title: 'Race / Ethnicity',
            content: <EthnicityForm control={control} />,
            expanded: false,
            id: '5',
            headingLevel: 'h4',
            className: 'accordian-item'
        }
    ];

    const handleRecordStatusChange = (status: RecordStatus, isChecked: boolean) => {
        // Add or remove record status from state
        if (isChecked) {
            setSelectedRecordStatus([...selectedRecordStatus, status]);
        } else {
            setSelectedRecordStatus(selectedRecordStatus.filter((r) => r !== status));
        }
    };

    const onSubmit: any = (body: any) => {
        // at least 1 record status must be selected
        if (selectedRecordStatus.length === 0) {
            return;
        }
        const rowData: PersonFilter = {
            firstName: body.firstName,
            lastName: body.lastName,
            recordStatus: selectedRecordStatus
        };
        body.dob && (rowData.dateOfBirth = body.dob);
        body.gender !== '- Select -' && (rowData.gender = body.gender);
        body.patientId && (rowData.id = body.patientId);

        body.address && (rowData.address = body.address);
        body.city && (rowData.city = body.city);
        body.state !== '- Select -' && (rowData.state = body.state);
        body.zip && validateZipCode(body.zip) && (rowData.zip = body.zip);
        body.phoneNumber && validatePhoneNumber(body.phoneNumber) && (rowData.phoneNumber = body.phoneNumber);
        body.email && (rowData.email = body.email);

        body.race !== '- Select -' && (rowData.race = body.race);
        body.ethnicity !== '- Select -' && (rowData.ethnicity = body.ethnicity);

        if (body.identificationNumber && body.identificationType !== '- Select -') {
            rowData.identification = {
                identificationNumber: body.identificationNumber,
                identificationType: body.identificationType
            };
        }
        handleSubmission(rowData);
    };

    return (
        <Form onSubmit={handleSubmit(onSubmit)} className="width-full maxw-full">
            <div style={{ height: `calc(100vh - 405px)`, overflowY: 'auto' }}>
                <Accordion items={simpleSearchItems} multiselectable={true} />
            </div>
            <Grid row className="bottom-search">
                <Grid col={12} className="padding-x-2">
                    <Button className="width-full clear-btn" type={'submit'}>
                        Search
                    </Button>
                </Grid>
                <Grid col={12} className="padding-x-2">
                    <Button
                        className="width-full clear-btn"
                        type={'button'}
                        onClick={() => {
                            reset({
                                firstName: '',
                                lastName: '',
                                address: '',
                                city: '',
                                state: '-Select-',
                                zip: '',
                                patientId: '',
                                dob: '',
                                gender: '-Select-',
                                phoneNumber: '',
                                email: '',
                                identificationNumber: '',
                                identificationType: '-Select-',
                                ethnicity: '-Select-',
                                race: '-Select-'
                            });
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
