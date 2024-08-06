import { NameFieldsExtended } from './NameFieldsExtended/NameFieldsExtended';
import { useAddPatientCodedValues } from '../useAddPatientCodedValues';
import { FormProvider, useForm } from 'react-hook-form';
import { NewPatientEntry } from '../NewPatientEntry';
import { Form } from '@trussworks/react-uswds';
import styles from './add-patient-extended.module.scss';

export const AddPatientExtended = () => {
    const coded = useAddPatientCodedValues();

    const methods = useForm<NewPatientEntry>({
        mode: 'onBlur'
    });

    return (
        <div className={styles.addPatientExtended}>
            <div className={styles.leftNav}> side navigation </div>
            <div className={styles.content}>
                <h2>New patient</h2>
                <FormProvider {...methods}>
                    <Form onSubmit={() => console.log()}>
                        <NameFieldsExtended coded={{ suffixes: coded.suffixes }} />
                    </Form>
                </FormProvider>
            </div>
            <div className={styles.rightNav}>
                <main className={styles.contentContainer}>
                    <aside className={styles.contentSidebar}>
                        <nav className={styles.contentNavigation}>
                            <h2 className={styles.contentNavigationTitle}>On this page</h2>
                            <div className="border-left border-base-lighter">
                                <a href="#section-Administrative">Administrative</a>
                                <a href="#section-Name">Name</a>
                                <a href="#section-Address">Address</a>
                                <a href="#section-PhoneEmail">Phone & email</a>
                                <a href="#section-Identification">Identification</a>
                                <a href="#section-Race">Race</a>
                                <a href="#section-Ethnicity">Ethnicity</a>
                                <a href="#section-SexBirth">Sex & birth</a>
                                <a href="#section-Mortality">Mortality</a>
                                <a href="#section-General">General patient information</a>
                            </div>
                        </nav>
                    </aside>
                </main>
            </div>
        </div>
    );
};
