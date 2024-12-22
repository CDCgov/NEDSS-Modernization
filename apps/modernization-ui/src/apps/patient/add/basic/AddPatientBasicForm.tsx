import { Card } from 'design-system/card';
import styles from './add-patient-basic-form.module.scss';
import { BasicPhoneEmailFields } from './phoneEmail';
import { BasicIdentificationRepeatingBlock } from './identification';
import { Controller, useFormContext } from 'react-hook-form';
import { BasicNewPatientEntry } from './entry';
import { AdministrativeEntryFields } from 'apps/patient/data/administrative/AdministrativeEntryFields';
import { NameEntryFields } from './name/NameEntryFields';
import { BasicRaceEthnicityFields } from './raceEthnicity/BasicEthnicityRaceFields';
import { BasicPersonalDetailsFields } from './personalDetails/BasicPersonalDetailsFields';
import { BasicAddressFields } from './address/BasicAddressFields';

export const AddPatientBasicForm = () => {
    const { control } = useFormContext<BasicNewPatientEntry>();
    return (
        <div className={styles.addPatientForm}>
            <div className={styles.formContent}>
                <Card
                    id="administrative"
                    title="Administrative"
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
                <Card id="personalDetails" title="Personal details">
                    <BasicPersonalDetailsFields />
                </Card>
                <Card id="address" title="Address">
                    <BasicAddressFields />
                </Card>
                <Card id="phoneEmail" title="Phone & email">
                    <BasicPhoneEmailFields />
                </Card>
                <Card id="raceEthnicity" title="Ethnicity & race">
                    <BasicRaceEthnicityFields />
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
