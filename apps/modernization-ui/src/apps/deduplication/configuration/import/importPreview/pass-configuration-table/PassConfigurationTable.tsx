import { BlockingAttributeLabels, MatchingAttributeLabels } from 'apps/deduplication/api/model/Labels';
import { matchMethodLabelMap, Pass } from 'apps/deduplication/api/model/Pass';
import { Card } from 'design-system/card';
import { Column, DataTable } from 'design-system/table';

type Props = {
    algorithm: { passes: Pass[] };
};
export const PassConfigurationTable = ({ algorithm }: Props) => {
    const columns: Column<Pass>[] = [
        {
            id: 'pass-name',
            name: 'Pass name',
            render: (pass) => pass.name
        },
        {
            id: 'pass-description',
            name: 'Description',
            render: (pass) => pass.description
        },
        {
            id: 'pass-blocking-criteria',
            name: 'Blocking criteria',
            render: (pass) => {
                return (
                    <>
                        {pass.blockingCriteria.map((b) => (
                            <div key={`blockingCriteria-${b}`}>{BlockingAttributeLabels[b].label}</div>
                        ))}
                    </>
                );
            }
        },
        {
            id: 'pass-matchin-criteria',
            name: 'Matching criteria',
            render: (pass) => {
                return (
                    <>
                        {pass.matchingCriteria.map((b) => (
                            <div key={`matchingCriteria-${b.attribute}`}>
                                {MatchingAttributeLabels[b.attribute].label}: {matchMethodLabelMap.get(b.method)}
                            </div>
                        ))}
                    </>
                );
            }
        },
        {
            id: 'pass-lower-bounds',
            name: 'Lower bounds',
            render: (pass) => pass.lowerBound
        },
        {
            id: 'pass-upper-bounds',
            name: 'Upper bounds',
            render: (pass) => pass.upperBound
        },
        {
            id: 'pass-active',
            name: 'Active',
            render: (pass) => (pass.active ? 'Yes' : 'No')
        }
    ];
    return (
        <Card id="pass-configuration-card" title="Pass configurations">
            <DataTable<Pass> id="data-element-table" columns={columns} data={algorithm.passes} />
        </Card>
    );
};
