import { yupResolver } from '@hookform/resolvers/yup';
import { Alert, Button, Form, Grid, Search, Table } from '@trussworks/react-uswds';
import { useContext, useEffect, useState } from 'react';
import { Controller, useForm } from 'react-hook-form';
import { useNavigate, useSearchParams } from 'react-router-dom';
import * as yup from 'yup';
import { DatePickerInput } from '../../components/FormInputs/DatePickerInput';
import { Input } from '../../components/FormInputs/Input';
import { SelectInput } from '../../components/FormInputs/SelectInput';
import { TableContent } from '../../components/TableContent/TableContent';
import { Gender, PersonFilter, useFindPatientsByFilterLazyQuery } from '../../generated/graphql/schema';
import { EncryptionControllerService } from '../../generated/services/EncryptionControllerService';
import { UserContext } from '../../providers/UserContext';
import './SimpleSearch.scss';

type FormTypes = {
    firstName: string;
    lastName: string;
    gender?: Gender | '- Select -';
    state?: string;
    city?: string;
    zip?: string;
    patientId?: string;
    dob?: Date;
};

const tableHead = [
    { name: 'Person', sortable: true },
    { name: 'Date of birth', sortable: false },
    { name: 'Type', sortable: true },
    { name: 'Last test', sortable: true },
    { name: 'Last result', sortable: true },
    { name: 'Action', sortable: false }
];

export const SimpleSearch = () => {
    const { state } = useContext(UserContext);
    const navigate = useNavigate();
    const [searchParams] = useSearchParams();

    const [getFilteredData, { data }] = useFindPatientsByFilterLazyQuery();
    const [submitted, setSubmitted] = useState(false);

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
        reset,
        setValue
    } = methods;

    useEffect(() => {
        const queryParam = searchParams?.get('q');
        if (queryParam && state.isLoggedIn) {
            EncryptionControllerService.decryptUsingPost({
                encryptedString: queryParam,
                authorization: `Bearer ${state.getToken()}`
            }).then((filter: PersonFilter) => {
                setValue('firstName', filter.firstName);
                setValue('lastName', filter.lastName);
                setValue('city', filter.city);
                setValue('zip', filter.zip);
                setValue('patientId', filter.id);
                setValue('dob', filter.dateOfBirth);
                setValue('gender', filter.gender);
                getFilteredData({ variables: { filter } })
                    // Sometimes 'then' doesn't trigger when using cache
                    .then(() => {
                        setSubmitted(true);
                    })
                    .finally(() => {
                        setSubmitted(true);
                    });
            });
        }
    }, [searchParams, state.isLoggedIn]);

    const onSubmit: any = async (body: FormTypes) => {
        // build filter from user input
        const filter: PersonFilter = {
            firstName: body.firstName,
            lastName: body.lastName
        };
        body.city && (filter.city = body.city);
        body.zip && (filter.zip = body.zip);
        body.patientId && (filter.id = body.patientId);
        body.dob && (filter.dateOfBirth = body.dob);
        body.gender !== '- Select -' && (filter.gender = body.gender);
        body.state !== '- Select -' && (filter.state = body.state);

        // send filter for encryption
        const encryptedFilter = await EncryptionControllerService.encryptUsingPost({
            authorization: `Bearer ${state.getToken()}`,
            object: filter
        });

        // URI encode encrypted filter
        const search = `?q=${encodeURIComponent(encryptedFilter.value)}`;

        // Update query param to trigger search
        navigate({
            pathname: '/search',
            search
        });
    };

    return (
        <div className="home-page bg-base-lightest padding-y-5">
            <Grid row className="flex-justify-center">
                <Grid col={10}>
                    <Grid row className="flex-justify-end">
                        <Grid col={6}>
                            <Search
                                size="big"
                                className="flex-justify-end"
                                placeholder="Search for a patient"
                                onSubmit={async (e: any) => {
                                    e.preventDefault();
                                    // build search filter from text
                                    const formatName = e.target[0].value.split(' ');
                                    const filter: PersonFilter = {};
                                    filter.firstName = formatName[0];
                                    filter.lastName = formatName.length > 1 ? formatName[1] : '';
                                    // send filter for encryption
                                    const encryptedFilter = await EncryptionControllerService.encryptUsingPost({
                                        authorization: `Bearer ${state.getToken()}`,
                                        object: filter
                                    });

                                    // URI encode encrypted filter
                                    const search = `?q=${encodeURIComponent(encryptedFilter.value)}`;

                                    navigate({
                                        pathname: '/advanced-search',
                                        search
                                    });
                                }}
                            />
                        </Grid>
                    </Grid>
                </Grid>
            </Grid>
            <Grid row className="flex-justify-center margin-y-2">
                <Grid
                    desktop={{ col: 10 }}
                    tablet={{ col: true }}
                    className="bg-white border-blue border padding-2 radius-md">
                    <Grid row className="flex-justify-center">
                        <Grid desktop={{ col: 10 }} tablet={{ col: true }} className="padding-2">
                            <div className="">
                                <h2 className="font-lang-lg margin-top-0 margin-bottom-3">Simple Search</h2>
                            </div>
                            <Form onSubmit={handleSubmit(onSubmit)} className="width-full maxw-full">
                                <Grid row gap={6} className="padding-bottom-3">
                                    <Grid col={6}>
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
                                    <Grid col={6}>
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
                                    <Grid col={6}>
                                        <Grid row gap={3}>
                                            <Grid col={4}>
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
                                            <Grid col={8}>
                                                <Controller
                                                    control={control}
                                                    name="dob"
                                                    render={({ field: { onChange } }) => (
                                                        <DatePickerInput
                                                            onChange={onChange}
                                                            name="dob"
                                                            htmlFor={'dob'}
                                                            label="Date Of Birth"
                                                        />
                                                    )}
                                                />
                                            </Grid>
                                        </Grid>
                                    </Grid>
                                    {/* <Grid col={6}>
                                        <Controller
                                            control={control}
                                            name="city"
                                            render={({ field: { onChange, value } }) => (
                                                <Input
                                                    onChange={onChange}
                                                    defaultValue={value}
                                                    type="text"
                                                    label="City"
                                                    name="city"
                                                    htmlFor="city"
                                                    id="city"
                                                />
                                            )}
                                        />
                                    </Grid>
                                    <Grid col={6}>
                                        <Controller
                                            control={control}
                                            name="state"
                                            render={({ field: { onChange } }) => (
                                                <SelectInput
                                                    onChange={onChange}
                                                    name="state"
                                                    htmlFor={'state'}
                                                    label="State"
                                                    options={stateList}
                                                />
                                            )}
                                        />
                                    </Grid>
                                    <Grid col={3}>
                                        <Controller
                                            control={control}
                                            name="zip"
                                            render={({ field: { onChange, value } }) => (
                                                <Input
                                                    onChange={onChange}
                                                    defaultValue={value}
                                                    type="text"
                                                    label="Zip"
                                                    name="zip"
                                                    htmlFor="zip"
                                                    id="zip"
                                                />
                                            )}
                                        />
                                    </Grid> */}
                                    <Grid col={6}>
                                        <Controller
                                            control={control}
                                            name="patientId"
                                            render={({ field: { onChange, value } }) => (
                                                <Input
                                                    onChange={onChange}
                                                    defaultValue={value}
                                                    type="text"
                                                    label="Patient ID"
                                                    name="patientId"
                                                    htmlFor="patientId"
                                                    id="patientId"
                                                />
                                            )}
                                        />
                                    </Grid>
                                    <Grid col={12} className="flex-align-self-end">
                                        <div className="grid-row flex-justify-end flex-align-end flex-wrap">
                                            <p
                                                onClick={() => navigate('/advanced-search')}
                                                className="margin-right-105 text-primary text-bold margin-bottom-05">
                                                Advanced Search
                                            </p>
                                            <Button
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
                                            <Button type={'submit'}>Search</Button>
                                        </div>
                                    </Grid>
                                </Grid>
                            </Form>
                        </Grid>
                    </Grid>
                </Grid>

                {data?.findPatientsByFilter && data?.findPatientsByFilter.content.length > 0 && (
                    <Grid desktop={{ col: 10 }} tablet={{ col: true }} className="bg-white margin-top-3 radius-md">
                        <Grid row className="flex-justify-center">
                            <Grid col={12} className="padding-4 border-bottom border-base-lightest">
                                <div className="grid-row flex-justify flex-align-center flex-wrap">
                                    <h2 className="font-ui-xl margin-top-0 margin-bottom-0">Search Results</h2>
                                    <div>
                                        <Button type={'button'} outline>
                                            Sort By
                                        </Button>
                                        <Button type={'button'} outline>
                                            Export Results
                                        </Button>
                                        <Button type={'button'}>Create New</Button>
                                    </div>
                                </div>
                            </Grid>
                            <Grid col={12} className="padding-4 table-checkbox">
                                <Table bordered={false} fullWidth>
                                    <TableContent tableHead={tableHead} tableBody={data?.findPatientsByFilter} />
                                </Table>
                            </Grid>
                        </Grid>
                    </Grid>
                )}
                {submitted && (!data?.findPatientsByFilter || data?.findPatientsByFilter.content.length === 0) && (
                    <div className="custom-alert" onClick={() => setSubmitted(false)}>
                        <Alert type="error" heading="No results found" headingLevel="h4">
                            <>
                                Make sure all words are spelled correctly.
                                <br />
                                Make sure inputs are in the correct fileds.
                                <br />
                                Try searching less fields.
                            </>
                        </Alert>
                    </div>
                )}
            </Grid>
        </div>
    );
};
