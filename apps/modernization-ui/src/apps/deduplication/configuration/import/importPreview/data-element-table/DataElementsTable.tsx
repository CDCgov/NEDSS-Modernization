import { DataElement, DataElements } from 'apps/deduplication/api/model/DataElement';
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
    const fieldNameLookup = new Map([
        ['firstName', 'First name'],
        ['lastName', 'Last name'],
        ['suffix', 'Suffix'],
        ['dateOfBirth', 'Date of birth'],
        ['sex', 'Current sex'],
        ['race', 'Race'],
        ['socialSecurity', 'SSN'],
        ['address', 'Street address 1'],
        ['city', 'City'],
        ['state', 'State'],
        ['zip', 'Zip'],
        ['county', 'County'],
        ['telephone', 'Phone number'],
        ['telecom', 'Telecom'],
        ['email', 'Email'],
        ['accountNumber', 'Account number'],
        ['driversLicenseNumber', 'Drivers license number'],
        ['medicaidNumber', 'Medicaid number'],
        ['medicalRecordNumber', 'Medical record number'],
        ['nationalUniqueIdentifier', 'National unique identifier'],
        ['patientExternalIdentifier', 'Patient external identifier'],
        ['patientInternalIdentifier', 'Patient internal identifier'],
        ['personNumber', 'Person number'],
        ['visaPassport', 'Visa/Passport'],
        ['wicIdentifier', 'WIC Identifier']
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
                    field: fieldNameLookup.get(key) ?? 'Unknown data element',
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
