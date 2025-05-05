import { FormProvider, useForm } from 'react-hook-form';
import { SkipLink } from 'SkipLink';
import { NavSection } from 'design-system/inPageNavigation/InPageNavigation';
import { NavigationGuard } from 'design-system/entry/navigation-guard';
import { Button } from 'design-system/button';
import { PatientCreatedPanel } from 'apps/patient/add/PatientCreatedPanel';
import { usePatientDataEntryMethod } from 'apps/patient/add/usePatientDataEntryMethod';
import { AddPatientLayout } from 'apps/patient/add/layout';
import { ExtendedNewPatientEntry } from './entry';
import { AddPatientExtendedForm } from './AddPatientExtendedForm';
import { useAddPatientExtendedDefaults } from './useAddPatientExtendedDefaults';
import { useAddExtendedPatient } from './useAddExtendedPatient';
import { AddExtendedPatientInteractionProvider } from './useAddExtendedPatientInteraction';

const sections: NavSection[] = [
    { id: 'administrative', label: 'Administrative' },
    { id: 'names', label: 'Name' },
    { id: 'addresses', label: 'Address' },
    { id: 'phoneEmails', label: 'Phone & email' },
    { id: 'identifications', label: 'Identification' },
    { id: 'races', label: 'Race' },
    { id: 'ethnicity', label: 'Ethnicity' },
    { id: 'sexAndBirth', label: 'Sex & birth' },
    { id: 'mortality', label: 'Mortality' },
    { id: 'generalInformation', label: 'General patient information' }
];

export const AddPatientExtended = () => {
    const interaction = useAddExtendedPatient();
    const { initialize } = useAddPatientExtendedDefaults();
    const { toBasic } = usePatientDataEntryMethod();

    const form = useForm<ExtendedNewPatientEntry>({
        defaultValues: initialize(),
        mode: 'onBlur',
        reValidateMode: 'onBlur'
    });

    const working = !form.formState.isValid || interaction.status !== 'waiting';

    const handleSave = form.handleSubmit(interaction.create);

    const handleCancel = () => {
        toBasic();
    };

    return (
        <>
            <SkipLink id="administrative.asOf" />
            <AddExtendedPatientInteractionProvider interaction={interaction}>
                {interaction.status === 'created' && <PatientCreatedPanel created={interaction.created} />}
                <FormProvider {...form}>
                    <AddPatientLayout
                        title="New patient - extended"
                        sections={sections}
                        actions={() => (
                            <>
                                <Button onClick={handleCancel} outline>
                                    Cancel
                                </Button>
                                <Button onClick={handleSave} disabled={working}>
                                    Save
                                </Button>
                            </>
                        )}>
                        <AddPatientExtendedForm
                            validationErrors={
                                interaction.status === 'invalid' ? interaction.validationErrors : undefined
                            }
                            setSubFormState={interaction.setSubFormState}
                        />
                    </AddPatientLayout>
                </FormProvider>
                <NavigationGuard
                    id="patient.create.extended.cancel"
                    form={form}
                    activated={interaction.status !== 'created'}
                />
            </AddExtendedPatientInteractionProvider>
        </>
    );
};
