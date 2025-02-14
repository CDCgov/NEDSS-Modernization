import { Card } from 'design-system/card';
import styles from './add-patient-basic-form.module.scss';
import { BasicPhoneEmailFields } from './phoneEmail';
import { BasicIdentificationRepeatingBlock } from './identification';
import { Controller, useFormContext } from 'react-hook-form';
import { useComponentSizing } from 'design-system/sizing';
import { BasicNewPatientEntry } from './entry';
import { AdministrativeEntryFields } from 'apps/patient/data/administrative/AdministrativeEntryFields';
import { NameEntryFields } from './name/NameEntryFields';
import { BasicRaceEthnicityFields } from './raceEthnicity/BasicEthnicityRaceFields';
import { BasicPersonalDetailsFields } from './personalDetails/BasicPersonalDetailsFields';
import { BasicAddressFields } from './address/BasicAddressFields';
import {
    ADDRESS_SECTION,
    ADMINISTRATIVE_SECTION,
    IDENTIFICATIONS_SECTION,
    NAME_SECTION,
    PERSONAL_DETAILS_SECTION,
    PHONE_EMAIL_SECTION,
    RACE_ETHNICITY_SECTION
} from './sections';

export const AddPatientBasicForm = () => {
    const { control } = useFormContext<BasicNewPatientEntry>();
    const sizing = useComponentSizing();

    return (
        <div className={styles.addPatientForm}>
            <div className={styles.formContent}>
                <Card
                    id={ADMINISTRATIVE_SECTION.id}
                    title={ADMINISTRATIVE_SECTION.label}
                    info={
                        <span>
                            <span className="required"> All fields marked with</span> are required
                        </span>
                    }>
                    <AdministrativeEntryFields sizing={sizing} />
                </Card>
                <Card id={NAME_SECTION.id} title={NAME_SECTION.label}>
                    <NameEntryFields sizing={sizing} />
                </Card>
                <Card id={PERSONAL_DETAILS_SECTION.id} title={PERSONAL_DETAILS_SECTION.label}>
                    <BasicPersonalDetailsFields sizing={sizing} />
                </Card>
                <Card id={ADDRESS_SECTION.id} title={ADDRESS_SECTION.label}>
                    <BasicAddressFields sizing={sizing} />
                </Card>
                <Card id={PHONE_EMAIL_SECTION.id} title={PHONE_EMAIL_SECTION.label}>
                    <BasicPhoneEmailFields sizing={sizing} />
                </Card>
                <Card id={RACE_ETHNICITY_SECTION.id} title={RACE_ETHNICITY_SECTION.label}>
                    <BasicRaceEthnicityFields sizing={sizing} />
                </Card>
                <Controller
                    control={control}
                    name="identifications"
                    render={({ field: { onChange, value } }) => (
                        <BasicIdentificationRepeatingBlock
                            id={IDENTIFICATIONS_SECTION.id}
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
