/* eslint-disable @typescript-eslint/no-unused-vars */
import { Accordion, Button, DatePicker, Form, Grid, Label } from '@trussworks/react-uswds';
import './AddPatient.scss';
import NameFields from './components/nameFields/NameFields';
import AddressFields, { InputAddressFields } from './components/addressFields/AddressFields';
import { AccordionItemProps } from '@trussworks/react-uswds/lib/components/Accordion/Accordion';
import PersonalDetails, { InputPersonalDetailsFields } from './components/personalDetails/PersonalDetails';
import ContactFields from './components/contactFields/ContactFields';
import EthnicityFields, { InputEthnicityFields } from './components/ethnicityFields/EthnicityFields';
import { useEffect, useState } from 'react';
import { ACTIVE_TAB, LeftBar } from './components/LeftBar/LeftBar';
import RaceFields from './components/Race/RaceFields';
import GeneralInformation from './components/generalInformation/generalInformation';
import { IdentificationFields } from './components/identificationFields/IdentificationFields';
import { useFieldArray, useForm } from 'react-hook-form';
import OtherInfoFields from './components/otherInfoFields/OtherInfoFields';
import { SuccessForm } from './components/SuccessForm/SuccessForm';

export default function AddPatient() {
    const [disabled, setDisabled] = useState<boolean>(true);
    const [successSubmit, setSuccessSubmit] = useState<boolean>(false);
    const [submitData, setSubmitData] = useState<any>();
    function isEmpty(obj: any) {
        for (const key in obj) {
            if (obj[key] !== undefined && obj[key] != '' && key !== 'recordStatus') return false;
        }
        return true;
    }

    const methods = useForm({
        defaultValues: {
            identification: [{ identificationType: null, assigningAuthority: null, identificationNumber: '' }],
            phoneNumbers: [{ cellPhone: null }],
            emailAddresses: [{ email: null }]
        }
    });
    const {
        handleSubmit,
        control,
        formState: { errors, isValid }
    } = methods;
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

    const [addressFields, setAddressFields]: [InputAddressFields, (inputNameFields: InputAddressFields) => void] =
        useState({
            streetAddress1: '',
            streetAddress2: '',
            city: '',
            state: '',
            zip: '',
            county: '',
            censusTract: '',
            country: ''
        });

    const [asOfDate, setAsOfDate]: [string, (asOfDate: string) => void] = useState(new Date().toISOString());

    useEffect(() => {
        setDisabled(!isValid);
    }, [isValid]);

    const submit = (data: any) => {
        setSubmitData(data);
        setSuccessSubmit(true);
    };

    window.addEventListener('load', () => {
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
    });

    return (
        <>
            {!successSubmit && (
                <Grid row>
                    <Grid col={3} className="bg-white border-right border-base-light">
                        <LeftBar activeTab={ACTIVE_TAB.PATIENT} />
                    </Grid>
                    <Grid col={9} className="margin-left-auto">
                        <Form onSubmit={handleSubmit(submit)} className="width-full max-width-full">
                            <Grid row className="page-title-bar bg-white">
                                <div className="width-full text-bold flex-row display-flex flex-align-center flex-justify">
                                    New patient
                                    <div className="button-group">
                                        <Button
                                            disabled={disabled}
                                            className="padding-x-3 add-patient-button"
                                            type={'button'}>
                                            Save and add new lab report
                                        </Button>
                                        <Button
                                            disabled={disabled}
                                            className="padding-x-3 add-patient-button"
                                            type={'submit'}>
                                            Save changes
                                        </Button>
                                    </div>
                                </div>
                            </Grid>
                            <div className="content">
                                <Grid row className="padding-3">
                                    <Grid col={9}>
                                        <GeneralInformation
                                            errors={errors}
                                            control={control}
                                            title="General information"
                                            id={'section-General_information'}
                                        />

                                        <NameFields id={'section-Name'} title="Name information" control={control} />
                                        <OtherInfoFields
                                            control={control}
                                            id={'section-Other'}
                                            title="Other information"
                                        />
                                        <AddressFields
                                            id={'section-Address'}
                                            title="Address"
                                            addressFields={addressFields}
                                            updateCallback={setAddressFields}
                                        />
                                        <ContactFields
                                            phoneNumberFields={phoneNumberFields}
                                            emailFields={emailFields}
                                            phoneNumberAppend={phoneNumberAppend}
                                            emailFieldAppend={emailFieldAppend}
                                            control={control}
                                            id={'section-Telephone'}
                                            title="Telephone"
                                        />
                                        <EthnicityFields id={'section-Ethnicity'} title="Ethnicity" />
                                        <RaceFields id={'section-Race'} title={'Race'} />
                                        <IdentificationFields
                                            fields={fields}
                                            append={append}
                                            control={control}
                                            id={'section-Identification'}
                                            title="Identification"
                                        />
                                    </Grid>

                                    <Grid col={3}>
                                        <main className="content-container">
                                            <aside className="content-sidebar">
                                                <nav className="content-navigation">
                                                    <h3 className="content-navigation-title margin-top-0 margin-bottom-1">
                                                        On this page
                                                    </h3>
                                                    <div className="border-left border-base-lighter">
                                                        <a href="#section-General_information">General Information</a>
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
            {successSubmit && <SuccessForm data={submitData} />}
        </>
    );
}
