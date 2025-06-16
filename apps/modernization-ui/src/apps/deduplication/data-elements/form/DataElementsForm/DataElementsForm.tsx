import { DataElementToMatchingAttribute } from 'apps/deduplication/api/model/Conversion';
import { MatchingAttributeLabels } from 'apps/deduplication/api/model/Labels';
import { Card } from 'design-system/card/Card';
import { Checkbox } from 'design-system/checkbox';
import { Hint } from 'design-system/hint';
import { useFormContext, useWatch } from 'react-hook-form';
import { DataElements } from '../../../api/model/DataElement';
import { DataElementRow } from '../DataElementRow/DataElementRow';
import styles from './DataElementsForm.module.scss';

const dataElementKeys: (keyof DataElements)[] = [
    'firstName',
    'lastName',
    'dateOfBirth',
    'sex',
    'race',
    'suffix',
    // Address Details
    'address',
    'city',
    'state',
    'zip',
    'county',
    'telephone',
    'email',
    // Identification Details
    'identifier',
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

type Props = {
    dataElements?: DataElements;
};
export const DataElementsForm = ({ dataElements }: Props) => {
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
        <Card
            id="dataElementsCard"
            title="Data elements"
            subtext="This table contains all the possible data elements that are available for use as person matching criteria.">
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
                                    aria-label={'Select All'}
                                    aria-labelledby={`select-all-checkbox`}
                                />
                            </th>
                            <th>Field</th>
                            <th className={styles.numericColumn}>
                                <span className={styles.headerWithIcon} aria-describedby="odds-ratio-hint">
                                    Odds ratio{' '}
                                    <Hint id="odds-ratio-hint">
                                        <b>Odds Ratio -</b> Once checked, enter predetermined odds ratio value for each
                                        data element as calculated from previous testing of local data
                                    </Hint>
                                </span>
                            </th>
                            <th className={styles.calculatedColumn}>
                                <span className={styles.headerWithIcon} aria-describedby="log-odds-hint">
                                    Log odds{' '}
                                    <Hint id="log-odds-hint">
                                        <b>Log odds -</b> The corresponding log odds value used by the algorithm will be
                                        calculated and displayed.
                                    </Hint>
                                </span>
                            </th>
                        </tr>
                        <tr className={styles.border}>
                            <th colSpan={4} />
                        </tr>
                    </thead>
                    <tbody>
                        {dataElementKeys.map((field) => (
                            <DataElementRow
                                key={`data-element-row-${field}`}
                                dataElements={dataElements}
                                field={field}
                                fieldName={MatchingAttributeLabels[DataElementToMatchingAttribute[field]].label}
                            />
                        ))}
                    </tbody>
                </table>
            </div>
        </Card>
    );
};
