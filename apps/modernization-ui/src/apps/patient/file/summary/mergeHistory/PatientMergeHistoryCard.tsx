import { Suspense, useMemo } from 'react';
import { Await } from 'react-router';
import { Section } from 'design-system/card/section/Section';
import { Card } from 'design-system/card';
import { Tag } from 'design-system/tag';
import { Column, SortableDataTable } from 'design-system/table';
import { PatientFileMergeHistory } from './mergeHistory';
import { MemoizedSupplier } from 'libs/supplying';
import { format } from 'date-fns';
import styles from './PatientMergeHistory.module.scss';

type PatientMergeHistoryCardProps = {
    id: string;
    provider: MemoizedSupplier<Promise<PatientFileMergeHistory[]>>;
};

const columns: Column<PatientFileMergeHistory>[] = [
    {
        id: 'supersededPersonLocalId',
        name: 'Patient ID',
        sortable: true,
        value: (row) => row.supersededPersonLocalId,
        sortIconType: 'numeric',
        render: (row) => <a href={`/patient/${row.supersededPersonLocalId}/summary`}>{row.supersededPersonLocalId}</a>
    },
    {
        id: 'supersededPersonLegalName',
        name: 'Patient Name',
        sortable: true,
        value: (row) => row.supersededPersonLegalName
    },
    {
        id: 'mergeTimestamp',
        name: 'Merge Date/Time',
        sortable: true,
        sortIconType: 'numeric',
        render: (row) => (row.mergeTimestamp ? format(new Date(row.mergeTimestamp), 'Pp') : '')
    },
    {
        id: 'mergedByUser',
        name: 'Merged By',
        sortable: true,
        value: (row) => row.mergedByUser
    }
];

const groupByTimestamp = (rows: PatientFileMergeHistory[]) =>
    rows.reduce<Record<string, PatientFileMergeHistory[]>>((acc, row) => {
        const key = row.mergeTimestamp ?? '---';
        if (!acc[key]) acc[key] = [];
        acc[key].push(row);
        return acc;
    }, {});

const InternalMergeHistoryCard = ({ id, data }: { id: string; data: PatientFileMergeHistory[] }) => {
    const grouped = useMemo(() => groupByTimestamp(data), [data]);
    const groupKeys = useMemo(
        () => Object.keys(grouped).sort((a, b) => new Date(b).getTime() - new Date(a).getTime()),
        [grouped]
    );

    return (
        <Card id={id} title="Merge history" flair={<Tag variant="default">{data.length}</Tag>} collapsible>
            <div className={styles.sectionCard}>
                {groupKeys.map((timestampKey, index) => {
                    const group = grouped[timestampKey];

                    return (
                        <Section
                            key={timestampKey}
                            id={`${id}-section-${index}`}
                            title="The following superseded patient records were merged with ${(patientFullName)}"
                            subtext={`${group.length} record${group.length === 1 ? '' : 's'}`}>
                            <SortableDataTable
                                id={`${id}-table-${index}`}
                                columns={columns}
                                data={group}
                                className={styles.dataTable}
                                sizing={'small'}
                            />
                        </Section>
                    );
                })}
            </div>
        </Card>
    );
};

const PatientMergeHistoryCard = ({ id, provider }: PatientMergeHistoryCardProps) => {
    return (
        <Suspense fallback={<div>Loading Merge History...</div>}>
            <Await resolve={provider.get()}>{(data) => <InternalMergeHistoryCard id={id} data={data} />}</Await>
        </Suspense>
    );
};

export { PatientMergeHistoryCard };
export type { PatientMergeHistoryCardProps };
