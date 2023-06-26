import {
    Button,
    ButtonGroup,
    Form,
    Grid,
    Icon,
    Modal,
    ModalFooter,
    ModalHeading,
    ModalRef,
    ModalToggleButton
} from '@trussworks/react-uswds';
import './AddPatient.scss';
import NameFields from './components/nameFields/NameFields';
import AddressFields, { InputAddressFields } from './components/addressFields/AddressFields';
import ContactFields from './components/contactFields/ContactFields';
import EthnicityFields from './components/ethnicityFields/EthnicityFields';
import { useEffect, useRef, useState } from 'react';
import { ACTIVE_TAB, LeftBar } from './components/LeftBar/LeftBar';
import RaceFields from './components/Race/RaceFields';
import GeneralInformation from './components/generalInformation/generalInformation';
import { IdentificationFields } from './components/identificationFields/IdentificationFields';
import { useFieldArray, useForm } from 'react-hook-form';
import OtherInfoFields from './components/otherInfoFields/OtherInfoFields';
import {
    NameUseCd,
    NewPatientPhoneNumber,
    NewPatientIdentification,
    PersonInput,
    useCreatePatientMutation
} from 'generated/graphql/schema';
import { format } from 'date-fns';
import { useNavigate } from 'react-router-dom';
import { useAddPatientCodedValues } from './useAddPatientCodedValues';
import { useCountyCodedValues, useLocationCodedValues } from 'location';

export default function AddPatient() {
    const navigate = useNavigate();

    const locations = useLocationCodedValues();

    const coded = useAddPatientCodedValues();

    const [successSubmit, setSuccessSubmit] = useState<boolean>(false);

    const [handleSavePatient] = useCreatePatientMutation();
    const modalRef = useRef<ModalRef>(null);

    function isEmpty(obj: any) {
        for (const key in obj) {
            if (obj[key] !== undefined && obj[key] != '' && key !== 'recordStatus') return false;
        }
        return true;
    }

    type formDefaultValueType = { [key: string]: [{ [key: string]: any }] };

    const defaultValues: formDefaultValueType = {
        identification: [{ type: '', authority: '', value: '' }],
        phoneNumbers: [{ cellPhone: null }],
        emailAddresses: [{ email: null }]
    };

    const {
        handleSubmit,
        control,
        formState: { errors, isValid },
        setValue
    } = useForm({
        defaultValues: defaultValues
    });

    const { fields, append } = useFieldArray({
        control,
        name: 'identification'
    });

    const { fields: phoneNumberFields, append: phoneNumberAppend } = useFieldArray({
        control,
        name: 'phoneNumbers'
    });

    const { fields: emailFields, append: emailFieldAppend } = useFieldArray({
        control,
        name: 'emailAddresses'
    });

    const [addressFields, setAddressFields] = useState<InputAddressFields>({
        streetAddress1: '',
        streetAddress2: '',
        city: '',
        state: '',
        zip: '',
        county: '',
        censusTract: '',
        country: ''
    });

    const submit = (data: any) => {
        setSuccessSubmit(true);
        const phoneNumbers: NewPatientPhoneNumber[] = [];
        data?.emailAddresses?.map((item: any, index: number) => {
            item.email = data?.[`emailAddresses_${index}`];
        });
        data?.phoneNumbers?.map((item: any, index: number) => {
            item.cellPhone = data?.[`cellPhone_${index}`];
            if (item.cellPhone) {
                phoneNumbers.push({
                    number: data?.[`cellPhone_${index}`],
                    type: 'CP',
                    use: 'MC'
                });
            }
        });
        if (data?.homePhone) {
            phoneNumbers.push({
                number: data?.homePhone,
                type: 'PH',
                use: 'H'
            });
        }
        if (data?.workPhone) {
            phoneNumbers.push({
                number: data?.workPhone,
                type: 'PH',
                use: 'WP'
            });
        }
        if (data?.race) {
            setValue('race', data?.race);
        }
        const addressObj = {
            streetAddress1: addressFields?.streetAddress1,
            streetAddress2: addressFields?.streetAddress2,
            state: data?.state,
            county: data?.county,
            zip: addressFields?.zip,
            censusTract: addressFields?.censusTract,
            country: data?.country,
            city: data?.city
        };
        const payload: PersonInput = {
            asOf: format(new Date(data?.asOf), `yyyy-MM-dd'T'HH:mm:ss.SSS'Z'`),
            comments: data?.additionalComments,
            names: [
                {
                    last: data?.lastName,
                    first: data?.firstName,
                    middle: data?.middleName,
                    suffix: data?.suffix,
                    use: NameUseCd.L
                }
            ],
            dateOfBirth: data?.dob,
            birthGender: data?.birthSex,
            currentGender: data?.gender,
            deceased: data?.deceased,
            maritalStatus: data?.maritalStatus,
            stateHIVCase: data?.hivId,
            ethnicity: data?.ethnicity,
            races: data?.race,
            phoneNumbers
        };

        const hasIdentificationValues = data?.identification.filter((item: NewPatientIdentification) =>
            Object.values(item).every((value) => value)
        );

        if (hasIdentificationValues?.length > 0) {
            payload.identifications = hasIdentificationValues;
        }
        if (data?.emailAddresses?.length > 0) {
            payload.emailAddresses = data?.emailAddresses.map((it: any) => it.email).filter((str: any) => str);
        }
        if (!isEmpty(addressObj)) {
            payload.addresses = [addressObj];
        }
        data?.dod && (payload.deceasedTime = format(new Date(data?.dod), `yyyy-MM-dd'T'HH:mm:ss.SSS'Z'`));
        const name =
            data?.lastName || data?.firstName
                ? `${data?.lastName || ''}${(data?.lastName && ', ') || ''}${data?.firstName || ''}`
                : 'Patient';
        handleSavePatient({
            variables: {
                patient: payload
            }
        }).then((re) => {
            if (re.data) {
                navigate(`/add-patient/patient-added?shortId=${re?.data?.createPatient.shortId}&name=${name}`);
            }
        });
    };

    useEffect(() => {
        const sections = Array.from(document.querySelectorAll('section[id]'));

        const scrollHandler: any = (entries: any) => {
            return entries.forEach((entry: any) => {
                const section: any = entry.target;
                const sectionId: any = section.id;
                const sectionLink: any = document.querySelector(`a[href="#${sectionId}"]`);
                if (entry.intersectionRatio > 0) {
                    section?.classList && section.classList.add('visible');
                    sectionLink?.classList && sectionLink.classList.add('visible');
                } else {
                    section?.classList && section.classList.remove('visible');
                    sectionLink?.classList && sectionLink.classList.remove('visible');
                }
            });
        };

        // Creates a new scroll observer
        const observer = new IntersectionObserver(scrollHandler);

        // noinspection JSCheckFunctionSignatures
        sections.forEach((section) => observer.observe(section));

        return () => observer.disconnect();
    }, []);

    return (
        <>
            {!successSubmit && (
                <Grid
                    row
                    style={{
                        height: 'calc(100vh - 82px)',
                        overflow: 'hidden'
                    }}>
                    <Grid col={3} className="bg-white border-right border-base-light">
                        <LeftBar activeTab={ACTIVE_TAB.PATIENT} />
                    </Grid>
                    <Grid col={9} className="margin-left-auto" style={{ position: 'relative' }}>
                        <Form onSubmit={handleSubmit(submit)} className="width-full max-width-full">
                            <Grid
                                row
                                className="page-title-bar bg-white"
                                style={{
                                    position: 'sticky',
                                    top: 0,
                                    zIndex: 1
                                }}>
                                <div className="width-full text-bold flex-row display-flex flex-align-center flex-justify">
                                    New patient
                                    <div className="button-group">
                                        {!isValid && (
                                            <Button className="padding-x-3 add-patient-button" type={'submit'}>
                                                Save changes
                                            </Button>
                                        )}

                                        {isValid && (
                                            <span>
                                                <ModalToggleButton
                                                    modalRef={modalRef}
                                                    opener
                                                    className="delete-btn add-patient margin-y-0 display-inline-flex">
                                                    Save changes
                                                </ModalToggleButton>
                                                <Modal
                                                    ref={modalRef}
                                                    id="example-incomplete-form-confirmation-modal"
                                                    aria-labelledby="incomplete-form-confirmation-modal-heading"
                                                    className="padding-0"
                                                    aria-describedby="incomplete-form-confirmation-modal-description">
                                                    <ModalHeading
                                                        id="incomplete-form-confirmation-modal-heading"
                                                        className="border-bottom border-base-lighter font-sans-lg padding-2">
                                                        Missing data
                                                    </ModalHeading>
                                                    <div className="margin-2 grid-row flex-no-wrap border-left-1 border-accent-warm flex-align-center">
                                                        <Icon.Warning className="font-sans-2xl margin-x-2" />
                                                        <h3>Are you sure?</h3>
                                                    </div>
                                                    <div className="margin-left-3">
                                                        <p
                                                            className="margin-left-9 padding-right-2"
                                                            id="incomplete-form-confirmation-modal-description">
                                                            You are about to add a new patient with missing data.
                                                        </p>
                                                    </div>
                                                    <ModalFooter className="border-top border-base-lighter padding-2 margin-left-auto">
                                                        <ButtonGroup>
                                                            <ModalToggleButton outline modalRef={modalRef} closer>
                                                                Go back
                                                            </ModalToggleButton>
                                                            <ModalToggleButton
                                                                onClick={handleSubmit(submit)}
                                                                modalRef={modalRef}
                                                                closer
                                                                className="padding-105 text-center">
                                                                Continue anyways
                                                            </ModalToggleButton>
                                                        </ButtonGroup>
                                                    </ModalFooter>
                                                </Modal>
                                            </span>
                                        )}
                                    </div>
                                </div>
                            </Grid>
                            <div className="content">
                                <Grid row className="padding-3" style={{ height: '100%', overflow: 'hidden' }}>
                                    <Grid col={9} style={{ height: 'calc(100vh - 160px)', overflow: 'auto' }}>
                                        {!isValid && successSubmit && (
                                            <div className="border-error bg-error-lighter margin-bottom-2 padding-right-1 grid-row flex-no-wrap border-left-1 flex-align-center">
                                                <Icon.Error className="font-sans-2xl margin-x-2" />
                                                <p id="form-error">
                                                    You have some invalid inputs. Please correct the invalid inputs
                                                    before moving forward.
                                                </p>
                                            </div>
                                        )}
                                        <GeneralInformation
                                            errors={errors}
                                            control={control}
                                            title="General information"
                                            id={'section-General_information'}
                                        />

                                        <NameFields
                                            id={'section-Name'}
                                            title="Name information"
                                            control={control}
                                            coded={{ suffixes: coded.suffixes }}
                                        />
                                        <OtherInfoFields
                                            control={control}
                                            id={'section-Other'}
                                            title="Other information"
                                            coded={{
                                                deceased: coded.deceased,
                                                genders: coded.genders,
                                                maritalStatuses: coded.maritalStatuses
                                            }}
                                        />
                                        <AddressFields
                                            id={'section-Address'}
                                            title="Address"
                                            control={control}
                                            addressFields={addressFields}
                                            updateCallback={(obj: any) => {
                                                setAddressFields(obj);

                                                // Since these 2 fields are controlled fields,
                                                // we need to setValue for it to reflect on the ui
                                                setValue('city', obj.city);
                                                setValue('state', obj.state);
                                            }}
                                        />
                                        <ContactFields
                                            phoneNumberFields={phoneNumberFields}
                                            emailFields={emailFields}
                                            phoneNumberAppend={phoneNumberAppend}
                                            emailFieldAppend={emailFieldAppend}
                                            control={control}
                                            id={'section-Telephone'}
                                            title="Telephone"
                                            errors={errors}
                                        />
                                        <EthnicityFields
                                            control={control}
                                            id={'section-Ethnicity'}
                                            title="Ethnicity"
                                            coded={{
                                                ethnicGroups: coded.ethnicGroups
                                            }}
                                        />
                                        <RaceFields
                                            control={control}
                                            id={'section-Race'}
                                            title={'Race'}
                                            coded={{ raceCategories: coded.raceCategories }}
                                        />
                                        <IdentificationFields
                                            fields={fields}
                                            append={append}
                                            control={control}
                                            id={'section-Identification'}
                                            title="Identification"
                                            coded={{
                                                identificationTypes: coded.identificationTypes,
                                                assigningAuthorities: coded.assigningAuthorities
                                            }}
                                        />
                                        <div style={{ height: `calc(20%)` }} />
                                    </Grid>

                                    <Grid col={3} style={{ alignSelf: 'flex-start' }}>
                                        <main className="content-container">
                                            <aside className="content-sidebar">
                                                <nav className="content-navigation">
                                                    <h3 className="content-navigation-title margin-top-0 margin-bottom-1">
                                                        On this page
                                                    </h3>
                                                    <div className="border-left border-base-lighter">
                                                        <a href="#section-General_information">General information</a>
                                                        <a href="#section-Name">Name information</a>
                                                        <a href="#section-Other">Other information</a>
                                                        <a href="#section-Address">Address</a>
                                                        <a href="#section-Telephone">Telephone</a>
                                                        <a href="#section-Ethnicity">Ethnicity</a>
                                                        <a href="#section-Race">Race</a>
                                                        <a href="#section-Identification">Identification</a>
                                                    </div>
                                                </nav>
                                            </aside>
                                        </main>
                                    </Grid>
                                </Grid>
                            </div>
                        </Form>
                    </Grid>
                </Grid>
            )}
        </>
    );
}
