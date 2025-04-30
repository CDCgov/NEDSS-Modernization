import { DataElementToMatchingAttribute } from 'apps/deduplication/api/model/Conversion';
import { DataElement, DataElements } from 'apps/deduplication/api/model/DataElement';
import { MatchingAttributeLabels } from 'apps/deduplication/api/model/Labels';
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
        }
    ];

    useEffect(() => {
        const data: DataElementEntry[] = Object.entries(dataElements)
            .filter((value) => value[1].active)
            .map(([key, value]) => {
                return {
                    field: MatchingAttributeLabels[DataElementToMatchingAttribute[key as keyof DataElements]].label,
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
