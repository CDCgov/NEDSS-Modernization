import { DataElement, DataElements } from 'apps/deduplication/api/model/DataElement';
import { MatchingAttributeLabelMap } from 'apps/deduplication/api/model/Labels';
import { MatchingAttribute } from 'apps/deduplication/api/model/Pass';
import { Card } from 'design-system/card';
import { Column, DataTable } from 'design-system/table';
import { useEffect, useState } from 'react';

type DataElementEntry = {
    field: string;
} & DataElement;

type Props = {
    dataElements: DataElements;
};
export const DataElementsTable = ({ dataElements }: Props) => {
    const [data, setData] = useState<DataElementEntry[]>([]);

    // maps DataElements property name to label
    const fieldNameLookup: Map<keyof DataElements, string | undefined> = new Map([
        ['firstName', MatchingAttributeLabelMap.get(MatchingAttribute.FIRST_NAME)?.label],
        ['lastName', MatchingAttributeLabelMap.get(MatchingAttribute.LAST_NAME)?.label],
        ['suffix', MatchingAttributeLabelMap.get(MatchingAttribute.SUFFIX)?.label],
        ['dateOfBirth', MatchingAttributeLabelMap.get(MatchingAttribute.BIRTHDATE)?.label],
        ['sex', MatchingAttributeLabelMap.get(MatchingAttribute.SEX)?.label],
        ['race', MatchingAttributeLabelMap.get(MatchingAttribute.RACE)?.label],
        ['socialSecurity', MatchingAttributeLabelMap.get(MatchingAttribute.SOCIAL_SECURITY)?.label],
        ['address', MatchingAttributeLabelMap.get(MatchingAttribute.ADDRESS)?.label],
        ['city', MatchingAttributeLabelMap.get(MatchingAttribute.CITY)?.label],
        ['state', MatchingAttributeLabelMap.get(MatchingAttribute.STATE)?.label],
        ['zip', MatchingAttributeLabelMap.get(MatchingAttribute.ZIP)?.label],
        ['county', MatchingAttributeLabelMap.get(MatchingAttribute.COUNTY)?.label],
        ['telephone', MatchingAttributeLabelMap.get(MatchingAttribute.PHONE)?.label],
        ['email', MatchingAttributeLabelMap.get(MatchingAttribute.EMAIL)?.label],
        ['accountNumber', MatchingAttributeLabelMap.get(MatchingAttribute.ACCOUNT_NUMBER)?.label],
        ['driversLicenseNumber', MatchingAttributeLabelMap.get(MatchingAttribute.DRIVERS_LICENSE_NUMBER)?.label],
        ['medicaidNumber', MatchingAttributeLabelMap.get(MatchingAttribute.MEDICAID_NUMBER)?.label],
        ['medicalRecordNumber', MatchingAttributeLabelMap.get(MatchingAttribute.MEDICAL_RECORD_NUMBER)?.label],
        ['medicareNumber', MatchingAttributeLabelMap.get(MatchingAttribute.MEDICARE_NUMBER)?.label],
        [
            'nationalUniqueIdentifier',
            MatchingAttributeLabelMap.get(MatchingAttribute.NATIONAL_UNIQUE_INDIVIDUAL_IDENTIFIER)?.label
        ],
        [
            'patientExternalIdentifier',
            MatchingAttributeLabelMap.get(MatchingAttribute.PATIENT_EXTERNAL_IDENTIFIER)?.label
        ],
        [
            'patientInternalIdentifier',
            MatchingAttributeLabelMap.get(MatchingAttribute.PATIENT_INTERNAL_IDENTIFIER)?.label
        ],
        ['personNumber', MatchingAttributeLabelMap.get(MatchingAttribute.PERSON_NUMBER)?.label],
        ['visaPassport', MatchingAttributeLabelMap.get(MatchingAttribute.VISA_PASSPORT)?.label],
        ['wicIdentifier', MatchingAttributeLabelMap.get(MatchingAttribute.WIC_IDENTIFIER)?.label]
    ]);

    const columns: Column<DataElementEntry>[] = [
        {
            id: 'data-element-field',
            name: 'Field',
            render(entry) {
                return <>{entry.field}</>;
            }
        },
        {
            id: 'data-element-odds-ratio',
            name: 'Odds ratio',
            render(entry) {
                return <>{entry.oddsRatio}</>;
            }
        },
        {
            id: 'data-element-log-odss',
            name: 'Log odds',
            render(entry) {
                return <>{entry.logOdds}</>;
            }
        },
        {
            id: 'data-element-threshold',
            name: 'Threshold',
            render(entry) {
                return <>{entry.threshold}</>;
            }
        }
    ];

    useEffect(() => {
        const data: DataElementEntry[] = Object.entries(dataElements)
            .filter((value) => value[1].active)
            .map(([key, value]) => {
                return {
                    field: fieldNameLookup.get(key as keyof DataElements) ?? 'Unknown data element',
                    ...value
                };
            });
        setData(data);
    }, [dataElements]);

    return (
        <Card id="data-elements-card" title="Data elements">
            <DataTable<DataElementEntry> id="data-element-table" columns={columns} data={data} />
        </Card>
    );
};
