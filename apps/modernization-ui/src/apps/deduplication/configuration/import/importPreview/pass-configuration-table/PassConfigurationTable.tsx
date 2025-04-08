import {
    blockingAttributeLabelMap,
    matchingAttributeLabelMap,
    matchMethodLabelMap,
    Pass
} from 'apps/deduplication/api/model/Pass';
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
            render(pass) {
                return <>{pass.name}</>;
            }
        },
        {
            id: 'pass-description',
            name: 'Description',
            render(pass) {
                return <>{pass.description}</>;
            }
        },
        {
            id: 'pass-blocking-criteria',
            name: 'Blocking criteria',
            render(pass) {
                return (
                    <>
                        {pass.blockingCriteria.map((b, k) => (
                            <div key={k}>{blockingAttributeLabelMap.get(b)?.label}</div>
                        ))}
                    </>
                );
            }
        },
        {
            id: 'pass-matchin-criteria',
            name: 'Matching criteria',
            render(pass) {
                return (
                    <>
                        {pass.matchingCriteria.map((b, k) => (
                            <div key={k}>
                                {matchingAttributeLabelMap.get(b.attribute)}: {matchMethodLabelMap.get(b.method)}
                            </div>
                        ))}
                    </>
                );
            }
        },
        {
            id: 'pass-lower-bounds',
            name: 'Lower bounds',
            render(pass) {
                return <>{pass.lowerBound}</>;
            }
        },
        {
            id: 'pass-upper-bounds',
            name: 'Upper bounds',
            render(pass) {
                return <>{pass.upperBound}</>;
            }
        },
        {
            id: 'pass-active',
            name: 'Active',
            render(pass) {
                return <>{pass.active ? 'Yes' : 'No'}</>;
            }
        }
    ];
    return (
        <Card id="pass-configuration-card" title="Pass configurations">
            <DataTable<Pass> id="data-element-table" columns={columns} data={algorithm.passes} />
        </Card>
    );
};
