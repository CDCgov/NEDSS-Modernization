import { Button } from 'components/button';
import { DataEntrySideNav } from '../DataEntrySideNav/DataEntrySideNav';
import { AddPatientExtendedForm } from './AddPatientExtendedForm';
import styles from './add-patient-extended.module.scss';

export const AddPatientExtended = () => {
    return (
        <div className={styles.addPatientExtended}>
            <DataEntrySideNav />
            <div className={styles.contet}>
                <header>
                    <h1>New patient - extended</h1>
                    <div className={styles.buttonGroup}>
                        <Button outline>Cancel</Button>
                        <Button>Save</Button>
                    </div>
                </header>
                <main>
                    <AddPatientExtendedForm />
                </main>
            </div>
        </div>
    );
};
