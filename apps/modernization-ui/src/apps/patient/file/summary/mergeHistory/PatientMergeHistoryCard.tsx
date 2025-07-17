import { Suspense } from 'react';
import { Await } from 'react-router';
import { Section } from 'design-system/card/section/Section';
import { Card } from 'design-system/card';
import { Tag } from 'design-system/tag';
import { DataTable, Column } from 'design-system/table';
import { PatientFileMergeHistory } from './mergeHistory';
import { MemoizedSupplier } from 'libs/supplying';
import { format } from 'date-fns';

type PatientMergeHistoryCardProps = {
    id: string;
    provider: MemoizedSupplier<Promise<PatientFileMergeHistory[]>>;
};

const InternalMergeHistoryCard = ({ id, data }: { id: string; data: PatientFileMergeHistory[] }) => {
    const columns: Column<PatientFileMergeHistory>[] = [
        {
            id: 'supersededPersonLocalId',
            name: 'Patient ID',
            render: (row) => row.supersededPersonLocalId
        },
        {
            id: 'supersededPersonLegalName',
            name: 'Patient Name',
            render: (row) => row.supersededPersonLegalName
        },
        {
            id: 'mergeTimestamp',
            name: 'Merge Date/Time',
            render: (row) => (row.mergeTimestamp ? format(new Date(row.mergeTimestamp), 'Pp') : '')
        },
        {
            id: 'mergedByUser',
            name: 'Merged By',
            render: (row) => row.mergedByUser
        }
    ];

    return (
        <Card id={id} title="Merge history" flair={<Tag variant="default">{data.length}</Tag>} collapsible>
            <Section
                id={`${id}-card`}
                title="The following superseded patient records were merged with ${(patientFullName)}"
                subtext={data.length + ' records'}>
                <DataTable id={`${id}-table`} columns={columns} data={data} />
            </Section>
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
