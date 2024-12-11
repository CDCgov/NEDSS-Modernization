import { Card } from 'design-system/card';
import styles from './add-patient-basic-form.module.scss';
import { BasicPhoneEmailFields } from './phoneEmail';
import { BasicIdentificationRepeatingBlock } from './identification';
import { Controller, useFormContext } from 'react-hook-form';
import { BasicNewPatientEntry } from './entry';

export const AddPatientBasicForm = () => {
    const { control } = useFormContext<BasicNewPatientEntry>();
    return (
        <div className={styles.addPatientForm}>
            <div className={styles.formContent}>
                <Card id="phoneEmail" title="Phone & Email">
                    <BasicPhoneEmailFields />
                </Card>
                <Controller
                    control={control}
                    name="identifications"
                    render={({ field: { onChange, value, name } }) => (
                        <BasicIdentificationRepeatingBlock
                            id={name}
                            values={value}
                            onChange={onChange}
                            isDirty={() => {}}
                        />
                    )}
                />
            </div>
        </div>
    );
};
