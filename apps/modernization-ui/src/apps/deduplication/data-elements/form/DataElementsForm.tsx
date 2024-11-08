import { Checkbox } from 'design-system/checkbox';
import { DataElementRow } from './DataElementRow';
import { useFormContext, useWatch } from 'react-hook-form';
import { DataElements } from '../DataElement';
import { Card } from 'design-system/card/Card';
import styles from './data-elements-form.module.scss';

const dataElementKeys: (keyof DataElements)[] = [
    'firstName',
    'lastName',
    'suffix',
    'birthDate',
    'mrn',
    'ssn',
    'driversLicense',
    'sex',
    'gender',
    'race',
    'address',
    'city',
    'state',
    'zip',
    'county',
    'telephone'
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
        form.trigger();
    };

    return (
        <Card id="dataElementsCard" title="Data elements">
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
                            <th className={styles.numericColumn}>M</th>
                            <th className={styles.numericColumn}>U</th>
                            <th className={styles.calculatedColumn}>Odds ratio</th>
                            <th className={styles.calculatedColumn}>Log odds</th>
                            <th className={styles.numericColumn}>Threshold</th>
                            <th />
                        </tr>
                        <tr className={styles.border}>
                            <th colSpan={8} />
                        </tr>
                    </thead>
                    <tbody>
                        <DataElementRow fieldName="First name" field="firstName" />
                        <DataElementRow fieldName="Last name" field="lastName" />
                        <DataElementRow fieldName="Suffix" field="suffix" />
                        <DataElementRow fieldName="Date of birth" field="birthDate" />
                        <DataElementRow fieldName="Current sex" field="sex" />
                        <DataElementRow fieldName="Gender" field="gender" />
                        <DataElementRow fieldName="Race" field="race" />
                        <DataElementRow fieldName="Address" field="address" />
                        <DataElementRow fieldName="City" field="city" />
                        <DataElementRow fieldName="State" field="state" />
                        <DataElementRow fieldName="Zip" field="zip" />
                        <DataElementRow fieldName="County" field="county" />
                        <DataElementRow fieldName="Phone number" field="telephone" />
                        <DataElementRow fieldName="MRN" field="mrn" />
                        <DataElementRow fieldName="SSN" field="ssn" />
                        <DataElementRow fieldName="Drivers license" field="driversLicense" />
                    </tbody>
                </table>
            </div>
        </Card>
    );
};
