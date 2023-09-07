import { useEffect, useRef, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { FormProvider, useForm } from 'react-hook-form';
import { PersonInput, useCreatePatientMutation } from 'generated/graphql/schema';
import { Button, Form, Grid, Icon, ModalRef } from '@trussworks/react-uswds';

import { StateCodedValues, useLocationCodedValues } from 'location';
import { ConfirmationModal } from 'confirmation';
import { useAddPatientCodedValues } from './useAddPatientCodedValues';
import { asPersonInput } from './asPersonInput';

import NameFields from './components/nameFields/NameFields';
import AddressFields from './components/addressFields/AddressFields';
import ContactFields from './components/contactFields/ContactFields';
import EthnicityFields from './components/ethnicityFields/EthnicityFields';
import { ACTIVE_TAB, LeftBar } from './components/LeftBar/LeftBar';
import RaceFields from './components/Race/RaceFields';
import GeneralInformation from './components/generalInformation/generalInformation';
import { IdentificationFields } from './components/identificationFields/IdentificationFields';
import OtherInfoFields from './components/otherInfoFields/OtherInfoFields';

import './AddPatient.scss';
import { VerifiableAdddress, AddressVerificationModal } from 'address/verification';
import { orNull } from 'utils';
import { DefaultNewPatentEntry, NewPatientEntry, initialEntry } from 'pages/patient/add';
import { isMissingFields } from './isMissingFields';

// The process of creating a patient is broken into steps once input is valid and the form has been submitted.
//
//      1.  Check Missing Fields
//          - Go back   > Return to Entry
//          - Continue  >> Verify Address
//      2.  Verify Address
//          if address has been entered
//              if verified >> Create Patient
//              if unverified
//                  - Go back   > Return to Entry
//                  - Continue  >> Create Patient
//              if verified-found
//                  - Go back   > Return to Entry
//                  - Continue without update       >> Create Patient (with entered address)
//                  - Update address and continue   >> Create Patient (with found address)
//              -
//          else    >>  Create Patient
//      3.  Create Patient

const asVerifiableAddress = (states: StateCodedValues, input: NewPatientEntry): VerifiableAdddress => ({
    address1: orNull(input.streetAddress1),
    city: orNull(input.city),
    state: states.byValue(input.state),
    zip: orNull(input.zip)
});

const withVerifiedAddress = (entry: NewPatientEntry, address: VerifiableAdddress): NewPatientEntry => ({
    ...entry,
    streetAddress1: address.address1,
    city: address.city,
    state: orNull(address.state?.value),
    zip: address.zip
});

type EntryState =
    | { step: 'entry' }
    | {
          step: 'verify-missing-fields' | 'verify-address' | 'create';
          entry: NewPatientEntry;
      }
    | { step: 'created'; id: number; name: string };

const resolveName = (input: PersonInput): string => {
    const name = input?.names && input?.names[0];

    return (name && [name.last, name.first].filter((e) => e).join(', ')) || 'Patient';
};

const AddPatient = () => {
    const navigate = useNavigate();

    const locations = useLocationCodedValues();

    const coded = useAddPatientCodedValues();

    const [handleSavePatient] = useCreatePatientMutation();
    const modalRef = useRef<ModalRef>(null);

    const [entryState, setEntryState] = useState<EntryState>({ step: 'entry' });

    const methods = useForm<NewPatientEntry, DefaultNewPatentEntry>({
        defaultValues: initialEntry()
    });

    const {
        handleSubmit,
        control,
        formState: { errors }
    } = methods;

    const formHasErrors = Object.keys(errors).length > 0;

    const cancelSubmission = () => {
        setEntryState({ step: 'entry' });
    };

    const evaluateMissingFields = (entry: NewPatientEntry) => {
        setEntryState({ step: isMissingFields(entry) ? 'verify-missing-fields' : 'verify-address', entry });
    };

    const evaluateAddress = () => {
        setEntryState((existing) => ('entry' in existing ? { ...existing, step: 'verify-address' } : existing));
    };

    const prepareCreate = (address: VerifiableAdddress) => {
        setEntryState((existing) =>
            'entry' in existing ? { step: 'create', entry: withVerifiedAddress(existing.entry, address) } : existing
        );
    };

    const create = (entry: NewPatientEntry) => {
        const payload = asPersonInput(entry);
        const name = resolveName(payload);

        handleSavePatient({
            variables: {
                patient: payload
            }
        }).then((result) => {
            if (result.data?.createPatient) {
                setEntryState({ step: 'created', id: result?.data?.createPatient.shortId, name: name });
            }
        });
    };

    useEffect(() => {
        //  controls the modal visibility
        const shown = entryState.step === 'verify-missing-fields' || entryState.step === 'verify-address';
        modalRef.current?.toggleModal(undefined, shown);
    }, [entryState.step]);

    useEffect(() => {
        if (entryState.step === 'create') {
            create(entryState.entry);
        } else if (entryState.step === 'created') {
            navigate(`/add-patient/patient-added?shortId=${entryState.id}&name=${entryState.name}`);
        }
    }, [entryState.step]);

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

        // Smooth scrolling function
        const smoothScroll = (element: any) => {
            element.scrollIntoView({
                behavior: 'smooth',
                block: 'start'
            });
        };

        // Add click event listeners to each sectionLink
        sections.forEach((section) => {
            const sectionId = section.id;
            const sectionLink = document.querySelector(`a[href="#${sectionId}"]`);
            if (sectionLink) {
                sectionLink.addEventListener('click', (event) => {
                    event.preventDefault();
                    smoothScroll(section);
                });
            }
        });

        return () => observer.disconnect();
    }, []);

    return (
        <Grid
            row
            style={{
                height: 'calc(100vh - 82px)',
                overflow: 'hidden'
            }}>
            {entryState.step === 'verify-missing-fields' && (
                <ConfirmationModal
                    modal={modalRef}
                    title="Missing data"
                    message="Are you sure?"
                    detail="You are about to add a new patient with missing data."
                    confirmText="Continue anyways"
                    onConfirm={evaluateAddress}
                    cancelText="Go back"
                    onCancel={cancelSubmission}
                />
            )}
            {entryState.step === 'verify-address' && (
                <AddressVerificationModal
                    modal={modalRef}
                    states={locations.states}
                    input={asVerifiableAddress(locations.states, entryState.entry)}
                    onConfirm={prepareCreate}
                    onCancel={cancelSubmission}
                />
            )}
            <Grid col={3} className="bg-white border-right border-base-light">
                <LeftBar activeTab={ACTIVE_TAB.PATIENT} />
            </Grid>
            <Grid col={9} className="margin-left-auto" style={{ position: 'relative' }}>
                <FormProvider {...methods}>
                    <Form
                        onSubmit={handleSubmit(evaluateMissingFields)}
                        className="width-full max-width-full"
                        autoComplete="off">
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
                                    <Button className="padding-x-3 add-patient-button" type={'submit'}>
                                        Save changes
                                    </Button>
                                </div>
                            </div>
                        </Grid>
                        <div className="content">
                            <Grid row className="padding-3" style={{ height: '100%', overflow: 'hidden' }}>
                                <Grid col={9} style={{ height: 'calc(100vh - 160px)', overflow: 'auto' }}>
                                    {formHasErrors && (
                                        <div className="border-error bg-error-lighter margin-bottom-2 padding-right-1 grid-row flex-no-wrap border-left-1 flex-align-center">
                                            <Icon.Error className="font-sans-2xl margin-x-2" />
                                            <p id="form-error">
                                                You have some invalid inputs. Please correct the invalid inputs before
                                                moving forward.
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
                                        errors={errors}
                                        id={'section-Other'}
                                        title="Other information"
                                        coded={{
                                            deceased: coded.deceased,
                                            genders: coded.genders,
                                            maritalStatuses: coded.maritalStatuses
                                        }}
                                    />
                                    <AddressFields id={'section-Address'} title="Address" coded={locations} />
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
                                    <div style={{ height: `calc(30%)` }} />
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
                </FormProvider>
            </Grid>
        </Grid>
    );
};

export { AddPatient };
