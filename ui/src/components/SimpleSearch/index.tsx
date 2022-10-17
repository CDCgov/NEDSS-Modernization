import { Accordion, Button, Form, Grid } from '@trussworks/react-uswds';
import { DatePickerInput } from '../FormInputs/DatePickerInput';
import { Input } from '../FormInputs/Input';
import { SelectInput } from '../FormInputs/SelectInput';
import * as yup from 'yup';
import { Controller, useForm } from 'react-hook-form';
import { yupResolver } from '@hookform/resolvers/yup';
import { Gender, PersonFilter, useFindPatientsByFilterLazyQuery } from '../../generated/graphql/schema';
import { AccordionItemProps } from '@trussworks/react-uswds/lib/components/Accordion/Accordion';

export const SimpleSearch = () => {
    const [getFilteredData] = useFindPatientsByFilterLazyQuery();
    const schema = yup.object().shape({
        firstName: yup.string().required('First name is required.'),
        lastName: yup.string().required('Last name is required.')
    });

    const methods = useForm({
        resolver: yupResolver(schema)
    });

    const {
        handleSubmit,
        control,
        formState: { errors },
        reset
    } = methods;

    const simpleSearchItems: AccordionItemProps[] = [
        {
            title: 'Simple Search',
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
                                    required
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
                                    required
                                    error={errors?.firstName && 'First name is required.'}
                                />
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
                    <Grid col={12}>
                        <Controller
                            control={control}
                            name="dob"
                            render={({ field: { onChange } }) => (
                                <DatePickerInput onChange={onChange} name="dob" htmlFor={'dob'} label="Date Of Birth" />
                            )}
                        />
                    </Grid>
                </>
            ),
            expanded: true,
            id: '1',
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
        body.dob && (rowData.DateOfBirth = body.dob);
        body.gender !== '- Select -' && (rowData.gender = body.gender);
        body.state !== '- Select -' && (rowData.state = body.state);

        let search = `?firstName=${rowData.firstName}&lastName=${rowData.lastName}`;
        rowData.city && (search = `${search}&city=${rowData.city}`);
        rowData.zip && (search = `${search}&zip=${rowData.zip}`);
        rowData.id && (search = `${search}&id=${rowData.id}`);
        rowData.DateOfBirth && (search = `${search}&DateOfBirth=${rowData.DateOfBirth}`);

        getFilteredData({
            variables: {
                filter: rowData
            }
        });
    };

    return (
        <Form onSubmit={handleSubmit(onSubmit)} className="width-full maxw-full">
            <Accordion items={simpleSearchItems} multiselectable={true} />
            <Grid col={12} className="margin-top-5 padding-x-3">
                <Button className="width-full" type={'submit'}>
                    Search
                </Button>
            </Grid>
            <Grid col={12} className="padding-x-3">
                <Button
                    className="width-full"
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
                    Clear
                </Button>
            </Grid>
        </Form>
    );
};
