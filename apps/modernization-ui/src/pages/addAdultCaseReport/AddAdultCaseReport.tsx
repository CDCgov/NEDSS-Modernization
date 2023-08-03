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
import './AddAdultCaseReport.scss';
import NameFields from './components/nameFields/NameFields';
import AddressFields from './components/addressFields/AddressFields';
import ContactFields from './components/contactFields/ContactFields';
import ReportingHealthDepartmentFields from './components/reportingHealthDepartmentFields/ReportingHealthDepartmentFields';
import EthnicityFields from './components/ethnicityFields/EthnicityFields';
import { useEffect, useRef, useState } from 'react';
import { ACTIVE_TAB, LeftBar } from './components/LeftBar/LeftBar';
import RaceFields from './components/Race/RaceFields';
import GeneralInformation from './components/generalInformation/generalInformation';
import { IdentificationFields } from './components/identificationFields/IdentificationFields';
import { FormProvider, useForm } from 'react-hook-form';
import OtherInfoFields from './components/otherInfoFields/OtherInfoFields';
import { FormInfo } from './components/formInfo/FormInfo';
import {
    NameUseCd,
    NewPatientPhoneNumber,
    NewPatientIdentification,
    PersonInput,
    useCreatePatientMutation
} from 'generated/graphql/schema';
import { format } from 'date-fns';
import { useNavigate } from 'react-router-dom';
import { useAddAdultCaseReportCodedValues } from './useAddAdultCaseReportCodedValues';
import { useCountyCodedValues, useLocationCodedValues } from 'location';
import { externalizeDateTime } from 'date';
import { usePatientProfile } from '../patient/profile/usePatientProfile';
import { PatientProfileSummary } from '../patient/profile/summary/PatientProfileSummaryACR';
import { DeletePatientMutation, useDeletePatientMutation } from 'generated/graphql/schema';
import { DeletabilityResult, resolveDeletability } from './resolveDeletability';

export default function addAdultCaseReport() {
    const [activeTab, setActiveTab] = useState<ACTIVE_TAB.FORM_INFO | ACTIVE_TAB.IDENTIFICATION | ACTIVE_TAB.DEMOGRAPHICS | ACTIVE_TAB.FACILITY | ACTIVE_TAB.LAB_DATA | ACTIVE_TAB.CLINICAL>(
        ACTIVE_TAB.FORM_INFO
    );

    const profile = usePatientProfile('1');


    const navigate = useNavigate();

    const locations = useLocationCodedValues();

    const coded = useAddAdultCaseReportCodedValues();

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
        identification: [{ type: null, authority: null, value: null }],
        phoneNumbers: [{ cellPhone: null }],
        emailAddresses: [{ email: null }]
    };

    const methods = useForm({
        defaultValues: defaultValues
    });

    const {
        handleSubmit,
        control,
        formState: { errors, isValid }
    } = methods;

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
        const addressObj = {
            streetAddress1: data?.streetAddress1,
            streetAddress2: data?.streetAddress2,
            state: data?.state,
            county: data?.county,
            zip: data?.zip,
            censusTract: data?.censusTract,
            country: data?.country,
            city: data?.city
        };
        const payload: PersonInput = {
            asOf: externalizeDateTime(data.asOf),
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

        handleSavePatient({
            variables: {
                patient: payload
            }
        }).then((re) => {
            if (re.data) {
                const name =
                    data?.lastName || data?.firstName
                        ? `${data?.lastName || ''}${(data?.lastName && ', ') || ''}${data?.firstName || ''}`
                        : 'Patient';
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
            <div className="height-full main-banner">
                <div className="main-body">
                    <div className="">
                        <div className="width-full text-bold flex-row display-flex flex-align-center flex-justify-left">
                            <img style={{ marginLeft: '5px' }} src={'/arrow-back.svg'}/>
                            <span style={{ marginLeft: '5px' }} className="light-title">Investigations / HIV</span>
                            <span style={{ marginLeft: '5px' }} className="dark-title">(CAS100030001GA01)</span>

                         </div>

                        <div className="margin-y-2 flex-row common-card bg-gray">
                            {profile && profile.summary && (
                                <PatientProfileSummary patient={profile.patient} summary={profile.summary} />
                            )}

                            <div className="grid-row flex-align-center common-card tabs-common-card">
                                <h6
                                    className={`${
                                        activeTab === ACTIVE_TAB.FORM_INFO && 'active'
                                    } text-normal type margin-y-3 font-sans-md padding-bottom-1 cursor-pointer margin-top-2 margin-bottom-0`}
                                    onClick={() => setActiveTab(ACTIVE_TAB.FORM_INFO)}>
                                    {ACTIVE_TAB.FORM_INFO}
                                </h6>
                                <h6
                                    className={`${
                                        activeTab === ACTIVE_TAB.IDENTIFICATION && 'active'
                                    } padding-bottom-1 type text-normal margin-y-3 font-sans-md margin-x-1 cursor-pointer margin-top-2 margin-bottom-0`}
                                    onClick={() => setActiveTab(ACTIVE_TAB.IDENTIFICATION)}>
                                    {ACTIVE_TAB.IDENTIFICATION}
                                </h6>
                                <h6
                                    className={`${
                                        activeTab === ACTIVE_TAB.DEMOGRAPHICS && 'active'
                                    } padding-bottom-1 type text-normal margin-y-3 font-sans-md margin-x-1 cursor-pointer margin-top-2 margin-bottom-0`}
                                    onClick={() => setActiveTab(ACTIVE_TAB.DEMOGRAPHICS)}>
                                    {ACTIVE_TAB.DEMOGRAPHICS}
                                </h6>
                                <h6
                                    className={`${
                                        activeTab === ACTIVE_TAB.FACILITY && 'active'
                                    } text-normal type margin-y-3 font-sans-md padding-bottom-1 margin-x-1 cursor-pointer margin-top-2 margin-bottom-0`}
                                    onClick={() => setActiveTab(ACTIVE_TAB.FACILITY)}>
                                    {ACTIVE_TAB.FACILITY}
                                </h6>
                                <h6
                                    className={`${
                                        activeTab === ACTIVE_TAB.LAB_DATA && 'active'
                                    } text-normal type margin-y-3 font-sans-md padding-bottom-1 margin-x-1 cursor-pointer margin-top-2 margin-bottom-0`}
                                    onClick={() => setActiveTab(ACTIVE_TAB.LAB_DATA)}>
                                    {ACTIVE_TAB.LAB_DATA}
                                </h6>
                                <h6
                                    className={`${
                                        activeTab === ACTIVE_TAB.CLINICAL && 'active'
                                    } text-normal type margin-y-3 font-sans-md padding-bottom-1 margin-x-1  cursor-pointer margin-top-2 margin-bottom-0`}
                                    onClick={() => setActiveTab(ACTIVE_TAB.CLINICAL)}>
                                    {ACTIVE_TAB.CLINICAL}
                                </h6>
                                <h6
                                    className={`${
                                        activeTab === ACTIVE_TAB.TREATMENT && 'active'
                                    } text-normal type margin-y-3 font-sans-md padding-bottom-1 margin-x-1 cursor-pointer margin-top-2 margin-bottom-0`}
                                    onClick={() => setActiveTab(ACTIVE_TAB.TREATMENT)}>
                                    {ACTIVE_TAB.TREATMENT}
                                </h6>
                                <h6
                                    className={`${
                                        activeTab === ACTIVE_TAB.TESTING_HISTORY && 'active'
                                    } text-normal type margin-y-3 font-sans-md padding-bottom-1 margin-x-1 cursor-pointer margin-top-2 margin-bottom-0`}
                                    onClick={() => setActiveTab(ACTIVE_TAB.TESTING_HISTORY)}>
                                    {ACTIVE_TAB.TESTING_HISTORY}
                                </h6>
                                <h6
                                    className={`${
                                        activeTab === ACTIVE_TAB.FOLLOWUP && 'active'
                                    } text-normal type margin-y-3 font-sans-md padding-bottom-1 margin-x-1 cursor-pointer margin-top-2 margin-bottom-0`}
                                    onClick={() => setActiveTab(ACTIVE_TAB.FOLLOWUP)}>
                                    {ACTIVE_TAB.FOLLOWUP}
                                </h6>
                                <h6
                                    className={`${
                                        activeTab === ACTIVE_TAB.LOCAL_FIELDS && 'active'
                                    } text-normal type margin-y-3 font-sans-md padding-bottom-1 margin-x-1 cursor-pointer margin-top-2 margin-bottom-0`}
                                    onClick={() => setActiveTab(ACTIVE_TAB.LOCAL_FIELDS)}>
                                    {ACTIVE_TAB.LOCAL_FIELDS}
                                </h6>
                                 <h6
                                    className={`${
                                        activeTab === ACTIVE_TAB.DUPLICATE_REVIEW && 'active'
                                    } text-normal type margin-y-3 font-sans-md padding-bottom-1 margin-x-1 cursor-pointer margin-top-2 margin-bottom-0`}
                                    onClick={() => setActiveTab(ACTIVE_TAB.DUPLICATE_REVIEW)}>
                                    {ACTIVE_TAB.DUPLICATE_REVIEW}
                                </h6>
                                 <h6
                                    className={`${
                                        activeTab === ACTIVE_TAB.RETIRED && 'active'
                                    } text-normal type margin-y-3 font-sans-md padding-bottom-1 margin-x-1 cursor-pointer margin-top-2 margin-bottom-0`}
                                    onClick={() => setActiveTab(ACTIVE_TAB.RETIRED)}>
                                    {ACTIVE_TAB.RETIRED}
                                </h6>
                                 <h6
                                    className={`${
                                        activeTab === ACTIVE_TAB.COMMENTS && 'active'
                                    } text-normal type margin-y-3 font-sans-md padding-bottom-1 cursor-pointer margin-top-2 margin-bottom-0`}
                                    onClick={() => setActiveTab(ACTIVE_TAB.COMMENTS)}>
                                    {ACTIVE_TAB.COMMENTS}
                                </h6>
                            </div>
                        </div>
                    </div>

                    <Grid
                        row
                        style={{
                            height: 'calc(100vh - 82px)',
                            overflow: 'hidden'
                        }}>
                        <Grid col={3} className="bg-white border-right border-base-light">
                            <LeftBar activeTab={ACTIVE_TAB.FORM_INFO} />
                        </Grid>
                        <Grid col={6} className="margin-left-auto" style={{ position: 'relative' }}>



                            <FormProvider {...methods}>
                                <Form
                                    onSubmit={handleSubmit(submit)}
                                    className="width-full max-width-full"
                                    autoComplete="off">
                                    <div className="content">
                                        <Grid row className="padding-left-3" style={{ height: '100%', overflow: 'hidden' }}>
                                            <Grid col={12} style={{ height: 'calc(100vh)', overflow: 'auto' }}>
                                                {!isValid && successSubmit && (
                                                    <div className="border-error bg-error-lighter margin-bottom-2 padding-right-1 grid-row flex-no-wrap border-left-1 flex-align-center">
                                                        <Icon.Error className="font-sans-2xl margin-x-2" />
                                                        <p id="form-error">
                                                            You have some invalid inputs. Please correct the invalid inputs
                                                            before moving forward.
                                                        </p>
                                                    </div>
                                                )}


                                                {activeTab === ACTIVE_TAB.IDENTIFICATION &&
                                                    <IdentificationFields
                                                        id={'section-Identification'}
                                                        title="Identification"
                                                        coded={{
                                                            identificationTypes: coded.identificationTypes,
                                                            assigningAuthorities: coded.assigningAuthorities
                                                        }}
                                                    />
                                                }

                                                 {activeTab === ACTIVE_TAB.FORM_INFO &&
                                                    <ReportingHealthDepartmentFields
                                                    id={'reporting-Health-Department-Fields'}
                                                    title="Reporting Health Department"
                                                    control={control}
                                                    coded={{ suffixes: coded.suffixes }}
                                                    />
                                                }


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
                                                    coded={{
                                                        ...locations,
                                                        byState: (state) => useCountyCodedValues(state).counties
                                                    }}
                                                />
                                                <ContactFields id={'section-Telephone'} title="Telephone" />
                                                <EthnicityFields
                                                    id={'section-Ethnicity'}
                                                    title="Ethnicity"
                                                    coded={{
                                                        ethnicGroups: coded.ethnicGroups
                                                    }}
                                                />
                                                <RaceFields
                                                    id={'section-Race'}
                                                    title={'Race'}
                                                    coded={{ raceCategories: coded.raceCategories }}
                                                />
                                                <IdentificationFields
                                                    id={'section-Identification'}
                                                    title="Identification"
                                                    coded={{
                                                        identificationTypes: coded.identificationTypes,
                                                        assigningAuthorities: coded.assigningAuthorities
                                                    }}
                                                />
                                                <div style={{ height: `calc(20%)` }} />
                                            </Grid>
                                        </Grid>
                                    </div>

                                </Form>
                            </FormProvider>
                        </Grid>
                        <Grid col={3} style={{ alignSelf: 'flex-start' }}>
                            <main className="content-container">
                                <aside className="content-sidebar">
                                    <nav className="content-navigation">
                                        <h3 className="content-navigation-title margin-top-0 margin-bottom-1">
                                            On this page
                                        </h3>
                                        <div className="border-left border-base-lighter">
                                            <a href="#section-General_information">
                                                General information
                                            </a>
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


            </div>
            )}
        </>
    );
}
