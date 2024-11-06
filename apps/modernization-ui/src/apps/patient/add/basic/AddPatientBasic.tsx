import { Button } from 'components/button';
import { AddPatientLayout, DataEntryLayout } from 'apps/patient/add/layout';
import styles from './add-patient-basic.module.scss';
import { sections } from './sections';
import { AddPatientBasicForm } from './AddPatientBasicForm';

export const AddPatientBasic = () => {
    return (
        <DataEntryLayout>
            <AddPatientLayout
                headerTitle="New patient"
                sections={sections}
                headerActions={() => (
                    <div className={styles.buttonGroup}>
                        <Button outline>Cancel</Button>
                        <Button>Save</Button>
                    </div>
                )}>
                <AddPatientBasicForm />
            </AddPatientLayout>
        </DataEntryLayout>
    );
};
