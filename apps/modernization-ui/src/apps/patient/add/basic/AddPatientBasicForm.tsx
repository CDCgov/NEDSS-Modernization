import { Card } from 'design-system/card';
import styles from './add-patient-basic-form.module.scss';
import { BasicPhoneEmailFields } from './phoneEmail';
import { AdministrativeEntryFields } from 'apps/patient/data/administrative/AdministrativeEntryFields';

export const AddPatientBasicForm = () => {
    return (
        <div className={styles.addPatientForm}>
            <div className={styles.formContent}>
                <Card
                    id="generalInformation"
                    title="General information"
                    info={
                        <span>
                            <span className="required"> All fields marked with</span> are required
                        </span>
                    }>
                    <AdministrativeEntryFields />
                </Card>
                <Card id="phoneEmail" title="Phone & Email">
                    <BasicPhoneEmailFields />
                </Card>
            </div>
        </div>
    );
};
