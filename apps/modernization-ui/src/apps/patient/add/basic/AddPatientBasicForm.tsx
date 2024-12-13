import { Card } from 'design-system/card';
import styles from './add-patient-basic-form.module.scss';
import { BasicPhoneEmailFields } from './phoneEmail';
import { BasicIdentificationRepeatingBlock } from './identification';
import { Controller, useFormContext } from 'react-hook-form';
import { BasicNewPatientEntry } from './entry';
import { AdministrativeEntryFields } from 'apps/patient/data/administrative/AdministrativeEntryFields';
import { NameEntryFields } from './name/NameEntryFields';

export const AddPatientBasicForm = () => {
    const { control } = useFormContext<BasicNewPatientEntry>();
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
                <Card id="name" title="Name">
                    <NameEntryFields />
                </Card>
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
