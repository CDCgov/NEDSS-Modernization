import { Checkbox } from 'design-system/checkbox';
import { DataElementRow } from './DataElementRow';
import { useFormContext, useWatch } from 'react-hook-form';
import { DataElements } from '../DataElement';
import { Card } from 'design-system/card/Card';
import styles from './data-elements-form.module.scss';

const dataElementKeys: (keyof DataElements)[] = [
    'firstName',
    'lastName',
    'dateOfBirth',
    'currentSex',
    'race',
    'suffix',
    // Address Details
    'streetAddress1',
    'city',
    'state',
    'zip',
    'county',
    'telecom',
    'telephone',
    'email',
    // Identification Details
    'accountNumber',
    'driversLicenseNumber',
    'medicaidNumber',
    'medicalRecordNumber',
    'medicareNumber',
    'nationalUniqueIdentifier',
    'patientExternalIdentifier',
    'patientInternalIdentifier',
    'personNumber',
    'socialSecurity',
    'visaPassport',
    'wicIdentifier'
];

export const DataElementsForm = () => {
    const form = useFormContext<DataElements>();
    const watch = useWatch({ control: form.control });

    // Checks all keys in DataElements for an active property of false.
    const hasInactive = (): boolean => {
        return dataElementKeys.map((k) => watch[k]?.active).includes(false);
    };

    const handleToggleAll = (toggle: boolean) => {
        dataElementKeys.forEach((k) => form.setValue(`${k}.active`, toggle));
    };

    return (
        <Card id="dataElementsCard" title="Data elements">
            <div className={styles.dataElementsForm}>
                <div className={styles.dataElementsForm}>
                    <table>
                        <thead>
                            <tr>
                                <th className={styles.checkbox}>
                                    <Checkbox
                                        name={'selectAll'}
                                        label=""
                                        id={'toggle-all-checkbox'}
                                        selected={!hasInactive()}
                                        onChange={handleToggleAll}
                                    />
                                </th>
                                <th>Field</th>
                                <th className={styles.numericColumn}>Odds ratio</th>
                                <th className={styles.calculatedColumn}>Log odds</th>
                                <th className={styles.numericColumn}>Threshold</th>
                            </tr>
                            <tr className={styles.border}>
                                <th colSpan={6} />
                            </tr>
                        </thead>
                        <tbody>
                            <DataElementRow fieldName="First name" field="firstName" />
                            <DataElementRow fieldName="Last name" field="lastName" />
                            <DataElementRow fieldName="Suffix" field="suffix" />
                            <DataElementRow fieldName="Date of birth" field="dateOfBirth" />
                            <DataElementRow fieldName="Current sex" field="currentSex" />
                            <DataElementRow fieldName="Race" field="race" />
                            <DataElementRow fieldName="SSN" field="socialSecurity" />
                            <DataElementRow fieldName="Street address 1" field="streetAddress1" />
                            <DataElementRow fieldName="City" field="city" />
                            <DataElementRow fieldName="State" field="state" />
                            <DataElementRow fieldName="Zip" field="zip" />
                            <DataElementRow fieldName="County" field="county" />
                            <DataElementRow fieldName="Phone number" field="telephone" />
                            <DataElementRow fieldName="Telecom" field="telecom" />
                            <DataElementRow fieldName="Email" field="email" />
                            <DataElementRow fieldName="Account number" field="accountNumber" />
                            <DataElementRow fieldName="Drivers license number" field="driversLicenseNumber" />
                            <DataElementRow fieldName="Medicaid number" field="medicaidNumber" />
                            <DataElementRow fieldName="Medical record number" field="medicalRecordNumber" />
                            <DataElementRow fieldName="National unique identifier" field="nationalUniqueIdentifier" />
                            <DataElementRow fieldName="Patient external identifier" field="patientExternalIdentifier" />
                            <DataElementRow fieldName="Patient internal identifier" field="patientInternalIdentifier" />
                            <DataElementRow fieldName="Person number" field="personNumber" />
                            <DataElementRow fieldName="Visa/Passport" field="visaPassport" />
                            <DataElementRow fieldName="WIC Identifier" field="wicIdentifier" />
                        </tbody>
                    </table>
                </div>
            </div>
        </Card>
    );
};
