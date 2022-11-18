import { Accordion, Button, Form, Grid } from '@trussworks/react-uswds';
import { DatePickerInput } from '../FormInputs/DatePickerInput';
import { Input } from '../FormInputs/Input';
import { SelectInput } from '../FormInputs/SelectInput';
import { Controller, useForm } from 'react-hook-form';
import { Gender, PersonFilter } from '../../generated/graphql/schema';
import { AccordionItemProps } from '@trussworks/react-uswds/lib/components/Accordion/Accordion';
import { AddressForm } from './AddressForm';
import { ContactForm } from './ContactForm';
import { IDForm } from './IdForm';
import { EthnicityForm } from './EthnicityForm';

type SimpleSearchProps = {
    handleSubmission: (data: PersonFilter) => void;
};

export const SimpleSearch = ({ handleSubmission }: SimpleSearchProps) => {
    const methods = useForm();

    const {
        handleSubmit,
        control,
        formState: { errors },
        reset
    } = methods;

    const simpleSearchItems: AccordionItemProps[] = [
        {
            title: 'Basic Info',
            content: (
                <>
                    <Grid col={12}>
                        <Controller
                            control={control}
                            name="lastName"
                            render={({ field: { onChange, value } }) => (
                                <Input
                                    onChange={onChange}
                                    type="text"
                                    label="Last Name"
                                    name="lastName"
                                    defaultValue={value}
                                    htmlFor="lastName"
                                    id="lastName"
                                    error={errors?.lastName && 'Last name is required.'}
                                />
                            )}
                        />
                    </Grid>
                    <Grid col={12}>
                        <Controller
                            control={control}
                            name="firstName"
                            render={({ field: { onChange, value } }) => (
                                <Input
                                    onChange={onChange}
                                    defaultValue={value}
                                    type="text"
                                    label="First Name"
                                    name="firstName"
                                    htmlFor="firstName"
                                    id="firstName"
                                    error={errors?.firstName && 'First name is required.'}
                                />
                            )}
                        />
                    </Grid>
                    <Grid col={12}>
                        <Controller
                            control={control}
                            name="dob"
                            render={({ field: { onChange } }) => (
                                <DatePickerInput onChange={onChange} name="dob" htmlFor={'dob'} label="Date Of Birth" />
                            )}
                        />
                    </Grid>
                    <Grid col={12}>
                        <Controller
                            control={control}
                            name="gender"
                            render={({ field: { onChange } }) => (
                                <SelectInput
                                    onChange={onChange}
                                    name="gender"
                                    htmlFor={'gender'}
                                    label="Gender"
                                    options={[
                                        { name: 'Male', value: Gender.M },
                                        { name: 'Female', value: Gender.F },
                                        { name: 'Other', value: Gender.U }
                                    ]}
                                />
                            )}
                        />
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
            content: <AddressForm control={control} />,
            expanded: false,
            id: '2',
            headingLevel: 'h4',
            className: 'accordian-item'
        },
        {
            title: 'Contact',
            content: <ContactForm control={control} />,
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

    const onSubmit: any = (body: any) => {
        const rowData: PersonFilter = {
            firstName: body.firstName,
            lastName: body.lastName
        };
        body.city && (rowData.city = body.city);
        body.zip && (rowData.zip = body.zip);
        body.patientId && (rowData.id = body.patientId);
        body.dob && (rowData.dateOfBirth = body.dob);
        body.gender !== '- Select -' && (rowData.gender = body.gender);
        body.state !== '- Select -' && (rowData.state = body.state);
        body.race !== '- Select -' && (rowData.race = body.race);
        body.ethnicity !== '- Select -' && (rowData.ethnicity = body.ethnicity);

        if (body.identificationNumber && body.identificationType) {
            rowData.identification = {
                identificationNumber: body.identificationNumber,
                identificationType: body.identificationType
            };
        }

        let search = `?firstName=${rowData.firstName}&lastName=${rowData.lastName}`;
        rowData.city && (search = `${search}&city=${rowData.city}`);
        rowData.zip && (search = `${search}&zip=${rowData.zip}`);
        rowData.id && (search = `${search}&id=${rowData.id}`);
        rowData.dateOfBirth && (search = `${search}&DateOfBirth=${rowData.dateOfBirth}`);
        handleSubmission(rowData);
    };

    return (
        <Form onSubmit={handleSubmit(onSubmit)} className="width-full maxw-full">
            <div style={{ height: `calc(100vh - 375px)`, overflowY: 'auto' }}>
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
                        onClick={() =>
                            reset({
                                lastName: '',
                                firstName: '',
                                city: '',
                                state: '',
                                zip: '',
                                patientId: ''
                            })
                        }
                        outline>
                        Clear all
                    </Button>
                </Grid>
            </Grid>
        </Form>
    );
};
