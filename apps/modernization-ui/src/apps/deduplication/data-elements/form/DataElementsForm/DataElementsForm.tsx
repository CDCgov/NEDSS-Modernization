import { Checkbox } from 'design-system/checkbox';
import { DataElementRow } from '../DataElementRow/DataElementRow';
import { useFormContext, useWatch } from 'react-hook-form';
import { DataElements } from '../../../api/model/DataElement';
import { Card } from 'design-system/card/Card';
import { useRef } from 'react';
import { Icon } from 'design-system/icon';
import styles from './DataElementsForm.module.scss';
import RichTooltip from 'design-system/richTooltip/RichTooltip';

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

type Props = {
    dataElements?: DataElements;
};
export const DataElementsForm = ({ dataElements }: Props) => {
    const form = useFormContext<DataElements>();
    const watch = useWatch({ control: form.control });

    const oddsRatioRef = useRef<HTMLSpanElement>(null);
    const logOddsRef = useRef<HTMLSpanElement>(null);
    const thresholdRef = useRef<HTMLSpanElement>(null);

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
                                <span className={styles.headerWithIcon}>
                                    Odds ratio{' '}
                                    <span ref={oddsRatioRef} className={styles.infoIcon}>
                                        <RichTooltip anchorRef={oddsRatioRef} marginTop={38}>
                                            <span>Odds Ratio - </span>
                                            <span style={{ fontWeight: 'normal' }}>
                                                Once checked, enter predetermined odds ratio value for each data element
                                                as calculated from previous testing of local data
                                            </span>
                                        </RichTooltip>
                                        <Icon name="info_outline" className="infoIcon" data-testid="infoIcon" />
                                    </span>
                                </span>
                            </th>
                            <th className={styles.calculatedColumn}>
                                <span className={styles.headerWithIcon}>
                                    Log odds{' '}
                                    <span ref={logOddsRef} className={styles.infoIcon}>
                                        <RichTooltip anchorRef={logOddsRef} marginTop={38}>
                                            <span>Log odds - </span>
                                            <span style={{ fontWeight: 'normal' }}>
                                                The corresponding log odds value used by the algorithm will be
                                                calculated and displayed.
                                            </span>
                                        </RichTooltip>
                                        <Icon name="info_outline" className="infoIcon" />
                                    </span>
                                </span>
                            </th>
                            <th className={styles.numericColumn}>
                                <span className={styles.headerWithIcon}>
                                    Threshold{' '}
                                    <span ref={thresholdRef} className={styles.infoIcon}>
                                        <RichTooltip anchorRef={thresholdRef} marginTop={38}>
                                            <span>Threshold - </span>
                                            <span style={{ fontWeight: 'normal' }}>
                                                Values above which two strings are said to be “similar enough” that
                                                they're probably the same thing. Values that are less than the threshold
                                                will be calculated as 0.
                                            </span>
                                        </RichTooltip>
                                        <Icon name="info_outline" className="infoIcon" />
                                    </span>
                                </span>
                            </th>
                        </tr>
                        <tr className={styles.border}>
                            <th colSpan={5} />
                        </tr>
                    </thead>
                    <tbody>
                        <DataElementRow dataElements={dataElements} fieldName="First name" field="firstName" />
                        <DataElementRow dataElements={dataElements} fieldName="Last name" field="lastName" />
                        <DataElementRow dataElements={dataElements} fieldName="Suffix" field="suffix" />
                        <DataElementRow dataElements={dataElements} fieldName="Date of birth" field="dateOfBirth" />
                        <DataElementRow dataElements={dataElements} fieldName="Current sex" field="sex" />
                        <DataElementRow dataElements={dataElements} fieldName="Race" field="race" />
                        <DataElementRow dataElements={dataElements} fieldName="SSN" field="socialSecurity" />
                        <DataElementRow dataElements={dataElements} fieldName="Street address 1" field="address" />
                        <DataElementRow dataElements={dataElements} fieldName="City" field="city" />
                        <DataElementRow dataElements={dataElements} fieldName="State" field="state" />
                        <DataElementRow dataElements={dataElements} fieldName="Zip" field="zip" />
                        <DataElementRow dataElements={dataElements} fieldName="County" field="county" />
                        <DataElementRow dataElements={dataElements} fieldName="Phone number" field="telephone" />
                        <DataElementRow dataElements={dataElements} fieldName="Telecom" field="telecom" />
                        <DataElementRow dataElements={dataElements} fieldName="Email" field="email" />
                        <DataElementRow dataElements={dataElements} fieldName="Account number" field="accountNumber" />
                        <DataElementRow
                            dataElements={dataElements}
                            fieldName="Drivers license number"
                            field="driversLicenseNumber"
                        />
                        <DataElementRow
                            dataElements={dataElements}
                            fieldName="Medicaid number"
                            field="medicaidNumber"
                        />
                        <DataElementRow
                            dataElements={dataElements}
                            fieldName="Medical record number"
                            field="medicalRecordNumber"
                        />
                        <DataElementRow
                            dataElements={dataElements}
                            fieldName="National unique identifier"
                            field="nationalUniqueIdentifier"
                        />
                        <DataElementRow
                            dataElements={dataElements}
                            fieldName="Patient external identifier"
                            field="patientExternalIdentifier"
                        />
                        <DataElementRow
                            dataElements={dataElements}
                            fieldName="Patient internal identifier"
                            field="patientInternalIdentifier"
                        />
                        <DataElementRow dataElements={dataElements} fieldName="Person number" field="personNumber" />
                        <DataElementRow dataElements={dataElements} fieldName="Visa/Passport" field="visaPassport" />
                        <DataElementRow dataElements={dataElements} fieldName="WIC Identifier" field="wicIdentifier" />
                    </tbody>
                </table>
            </div>
        </Card>
    );
};
