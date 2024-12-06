import { useEffect, useRef, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { FormProvider, useForm } from 'react-hook-form';
import { useCreatePatientMutation } from 'generated/graphql/schema';
import { Button, Form, Grid, Icon, ModalRef } from '@trussworks/react-uswds';
import { StateCodedValues, useLocationCodedValues } from 'location';
import { useAddPatientCodedValues } from 'apps/patient/add/useAddPatientCodedValues';
import { asPersonInput } from 'apps/patient/add/asPersonInput';
import { NameFields } from 'apps/patient/add/nameFields/NameFields';
import AddressFields from 'apps/patient/add/addressFields/AddressFields';
import ContactFields from 'apps/patient/add/contactFields/ContactFields';
import EthnicityFields from 'apps/patient/add/ethnicityFields/EthnicityFields';
import RaceFields from 'apps/patient/add/Race/RaceFields';
import GeneralInformation from 'apps/patient/add/generalInformation/generalInformation';
import { IdentificationFields } from 'apps/patient/add/identificationFields/IdentificationFields';
import OtherInfoFields from 'apps/patient/add/otherInfoFields/OtherInfoFields';
import { VerifiableAdddress, AddressVerificationModal } from 'address/verification';
import { orNull } from 'utils';
import { DefaultNewPatentEntry, initialEntry, NewPatientEntry } from 'apps/patient/add';
import { usePreFilled } from 'apps/patient/add/usePreFilled';
import { useConfiguration } from 'configuration';
import { useBasicExtendedTransition } from './useBasicExtendedTransition';
import { DataEntryMenu } from './DataEntryMenu';
import { Shown } from 'conditional-render';
import { PatientCreatedPanel } from './PatientCreatedPanel';

import './AddPatient.scss';
import { CreatedPatient } from './api';
import { useSearchFromAddPatient } from 'apps/search/patient/add/useSearchFromAddPatient';
import { useLocation } from 'react-router-dom';

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
    state: states.byValue(input.state?.value),
    zip: orNull(input.zip)
});

const withVerifiedAddress = (entry: NewPatientEntry, address: VerifiableAdddress): NewPatientEntry => ({
    ...entry,
    streetAddress1: address.address1,
    city: address.city,
    state: address.state,
    zip: address.zip
});

type EntryState =
    | { step: 'entry' }
    | {
          step: 'verify-missing-fields' | 'verify-address' | 'create';
          entry: NewPatientEntry;
      }
    | { step: 'created'; created: CreatedPatient };

const AddPatient = () => {
    const locations = useLocationCodedValues();
    const coded = useAddPatientCodedValues();

    const [handleSavePatient] = useCreatePatientMutation();
    const modalRef = useRef<ModalRef>(null);
    const { features } = useConfiguration();

    const [entryState, setEntryState] = useState<EntryState>({ step: 'entry' });

    const prefilled = usePreFilled(initialEntry());

    const { toExtended } = useBasicExtendedTransition();

    const methods = useForm<NewPatientEntry, DefaultNewPatentEntry>({
        defaultValues: {
            ...initialEntry(),
            country: {
                value: '840',
                name: 'United States'
            }
        },
        mode: 'onBlur'
    });

    const {
        handleSubmit,
        formState: { errors }
    } = methods;

    useEffect(() => {
        methods.reset(prefilled, { keepDefaultValues: true });
    }, [prefilled, methods.reset]);

    const formHasErrors = Object.keys(errors).length > 0;

    const cancelSubmission = () => {
        setEntryState({ step: 'entry' });
    };

    const evaluateMissingFields = (entry: NewPatientEntry) => {
        setEntryState({ step: 'verify-address', entry });
    };

    const prepareCreate = (address: VerifiableAdddress) => {
        setEntryState((existing) =>
            'entry' in existing ? { step: 'create', entry: withVerifiedAddress(existing.entry, address) } : existing
        );
    };

    const create = (entry: NewPatientEntry) => {
        const payload = asPersonInput(entry);

        handleSavePatient({
            variables: {
                patient: {
                    ...payload,
                    // prevent value of '' being passed for deceased
                    deceased: payload.deceased ? payload.deceased : undefined
                }
            }
        }).then((result) => {
            if (result.data?.createPatient) {
                const created: CreatedPatient = {
                    shortId: result?.data?.createPatient.shortId,
                    id: result?.data?.createPatient.id,
                    name: {
                        first: entry.firstName ?? undefined,
                        last: entry.lastName ?? undefined
                    }
                };

                setEntryState({
                    step: 'created',
                    created
                });
            }
        });
    };

    const { toSearch } = useSearchFromAddPatient();
    const location = useLocation();
    const handleCancel = () => {
        toSearch(location.state.criteria);
    };

    useEffect(() => {
        //  controls the modal visibility
        const shown =
            entryState.step === 'verify-missing-fields' ||
            entryState.step === 'verify-address' ||
            entryState.step === 'created';
        modalRef.current?.toggleModal(undefined, shown);
    }, [entryState.step]);

    useEffect(() => {
        if (entryState.step === 'create') {
            create(entryState.entry);
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
            {entryState.step === 'verify-address' && (
                <AddressVerificationModal
                    modal={modalRef}
                    states={locations.states}
                    input={asVerifiableAddress(locations.states, entryState.entry)}
                    onConfirm={prepareCreate}
                    onCancel={cancelSubmission}
                />
            )}
            <Shown when={entryState.step === 'created'}>
                {entryState.step === 'created' && <PatientCreatedPanel created={entryState.created} />}
            </Shown>
            <Grid col={3} className="bg-white">
                <DataEntryMenu />
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
                                <h1 className="new-patient-title margin-0">New patient</h1>
                                <div className="nav-buttons">
                                    {features.patient?.add?.extended?.enabled && (
                                        <Button
                                            type="button"
                                            onClick={handleSubmit(toExtended)}
                                            outline
                                            className="add-patient-button">
                                            Add extended data
                                        </Button>
                                    )}
                                    <Button
                                        className="add-patient-button"
                                        onClick={handleCancel}
                                        type={'button'}
                                        outline>
                                        Cancel
                                    </Button>
                                    <Button
                                        className="add-patient-button"
                                        type={'button'}
                                        onClick={handleSubmit(evaluateMissingFields)}>
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
                                        title="General information"
                                        id={'section-General_information'}
                                    />

                                    <NameFields
                                        id={'section-Name'}
                                        title="Name information"
                                        coded={{ suffixes: coded.suffixes }}
                                    />
                                    <OtherInfoFields
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
                                    <div style={{ height: `calc(38%)` }} />
                                </Grid>

                                <Grid col={3} style={{ alignSelf: 'flex-start' }}>
                                    <main className="content-container">
                                        <aside className="content-sidebar">
                                            <nav className="content-navigation">
                                                <h2 className="content-navigation-title margin-top-0 margin-bottom-1">
                                                    On this page
                                                </h2>
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
