import { Card } from '../card/Card';
import styles from './add-patient-basic-form.module.scss';
import { BasicPhoneEmailFields } from './phoneEmail';

export const AddPatientBasicForm = () => {
    return (
        <div className={styles.addPatientForm}>
            <div className={styles.formContent}>
                <Card id="phoneEmail" title="Phone & Email">
                    <BasicPhoneEmailFields />
                </Card>
            </div>
        </div>
    );
};
