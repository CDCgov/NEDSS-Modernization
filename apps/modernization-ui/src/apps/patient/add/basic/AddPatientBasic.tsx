import { useCallback, useEffect } from 'react';
import { useLocation } from 'react-router';
import { FormProvider, useForm } from 'react-hook-form';
import { Button } from 'components/button';
import { NavigationGuard } from 'design-system/entry/navigation-guard';
import { sections } from './sections';
import { AddPatientBasicForm } from './AddPatientBasicForm';
import { BasicNewPatientEntry, initial } from './entry';
import { useAddBasicPatient } from './useAddBasicPatient';
import { AddPatientLayout } from '../layout';
import { PatientCreatedPanel } from '../PatientCreatedPanel';
import { usePatientDataEntryMethod } from '../usePatientDataEntryMethod';
import { useAddPatientBasicDefaults } from './useAddPatientBasicDefaults';
import { useSearchFromAddPatient } from '../useSearchFromAddPatient';

import { SkipLink } from 'SkipLink';

export const AddPatientBasic = () => {
    const { defaults } = useAddPatientBasicDefaults();

    const form = useForm<BasicNewPatientEntry>({
        defaultValues: initial(),
        mode: 'onBlur'
    });

    useEffect(() => {
        if (defaults) {
            form.reset(defaults, { keepDefaultValues: true });
        }
    }, [defaults]);

    const { toExtended } = usePatientDataEntryMethod();

    const interaction = useAddBasicPatient();
    const handleSave = form.handleSubmit(interaction.create);

    const { toSearch } = useSearchFromAddPatient();
    const location = useLocation();

    const backToSearch = useCallback(
        () => toSearch(location.state?.criteria?.encrypted),
        [toSearch, location.state?.criteria?.encrypted]
    );

    const handleExtended = useCallback(
        form.handleSubmit((data) => toExtended(data, location.state?.criteria)),
        [form.handleSubmit, toExtended, location.state?.criteria]
    );

    const handleFormIsValid = (valid: boolean) => {
        interaction.setCanSave(valid);
    };

    const working = !form.formState.isValid || !interaction.canSave || interaction.status !== 'waiting';

    return (
        <>
            <SkipLink id="administrative.asOf" />
            {interaction.status === 'created' && <PatientCreatedPanel created={interaction.created} />}
            <FormProvider {...form}>
                <AddPatientLayout
                    title="New patient"
                    sections={sections}
                    actions={() => (
                        <>
                            <Button type="button" onClick={handleExtended} secondary disabled={working}>
                                Add extended data
                            </Button>
                            <Button onClick={backToSearch} secondary>
                                Cancel
                            </Button>
                            <Button type="submit" onClick={handleSave} disabled={working}>
                                Save
                            </Button>
                        </>
                    )}>
                    <AddPatientBasicForm isValid={handleFormIsValid} />
                </AddPatientLayout>
            </FormProvider>
            <NavigationGuard
                id="patient.create.basic.cancel"
                form={form}
                allowed={'/patient/add/extended'}
                activated={interaction.status !== 'created'}
            />
        </>
    );
};
