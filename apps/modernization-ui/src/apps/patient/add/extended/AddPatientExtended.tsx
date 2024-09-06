import { Button } from 'components/button';
import { DataEntrySideNav } from '../DataEntrySideNav/DataEntrySideNav';
import styles from './add-patient-extended.module.scss';
import { AddPatientExtendedForm } from './AddPatientExtendedForm';

export const AddPatientExtended = () => {
    return (
        <div className={styles.addPatientExtended}>
            <DataEntrySideNav />
            <div className={styles.contet}>
                <header>
                    <h2>New patient - extended</h2>
                    <div className={styles.buttonGroup}>
                        <Button outline>Cancel</Button>
                        <Button>Save</Button>
                    </div>
                </header>
                <main>
                    <AddPatientExtendedForm />
                    <aside>
                        <nav>
                            <div className={styles.navTitle}>On this page</div>
                            <div className={styles.navOptions}>
                                <a href="#section-General_information">Administrative</a>
                                <a href="#section-Name">Name</a>
                                <a href="#section-Other">Address</a>
                                <a href="#section-Address">Phone & email</a>
                                <a href="#section-Telephone">Identification</a>
                                <a href="#section-Ethnicity">Race</a>
                                <a href="#section-Race">Ethnicity</a>
                                <a href="#section-Identification">Sex & birth</a>
                                <a href="#section-Identification">Mortality</a>
                                <a href="#section-Identification">General patient information</a>
                            </div>
                        </nav>
                    </aside>
                </main>
            </div>
        </div>
    );
};
