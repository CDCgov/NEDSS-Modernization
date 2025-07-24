import { MergeCandidate, MergeName } from '../../../../../api/model/MergeCandidate';
import { NameId } from '../../../merge-review/model/PatientMergeForm';
import { format, parseISO } from 'date-fns';
import { MergePreviewTableCard } from '../shared/preview-card-table/MergePreviewTableCard';
import { Column } from 'design-system/table';

type NameEntry = {
    personUid: string;
    sequence: string;
    asOf?: string;
    type?: string;
    prefix?: string;
    last?: string;
    first?: string;
    middle?: string;
    suffix?: string;
    degree?: string;
};

type NameProps = {
    selectedNames: NameId[];
    mergeCandidates: MergeCandidate[];
};

export const PreviewName = ({ selectedNames, mergeCandidates }: NameProps) => {
    // Derive detailedNames by matching selectedNames against mergeCandidates' names
    const detailedNames: MergeName[] = mergeCandidates
        .flatMap((mc) => mc.names)
        .filter((name) =>
            selectedNames.some((sn) => sn.personUid === name.personUid && String(sn.sequence) === String(name.sequence))
        );

    const initialNames: NameEntry[] = detailedNames
        .toSorted((a, b) => (parseISO(a.asOf) > parseISO(b.asOf) ? -1 : 1))
        .map((n) => ({
            ...n,
            asOf: format(parseISO(n.asOf), 'MM/dd/yyyy')
        }));

    const columns: Column<NameEntry>[] = [
        {
            id: 'asOf',
            name: 'As of',
            render: (entry: NameEntry) => entry.asOf ?? '---',
            value: (entry: NameEntry) => entry.asOf,
            sortable: true
        },
        {
            id: 'type',
            name: 'Type',
            render: (entry: NameEntry) => entry.type ?? '---',
            value: (entry: NameEntry) => entry.type,
            sortable: true
        },
        {
            id: 'prefix',
            name: 'Prefix',
            render: (entry: NameEntry) => entry.prefix ?? '---',
            value: (entry: NameEntry) => entry.prefix,
            sortable: true
        },
        {
            id: 'last',
            name: 'Last',
            render: (entry: NameEntry) => entry.last ?? '---',
            value: (entry: NameEntry) => entry.last,
            sortable: true
        },
        {
            id: 'first',
            name: 'First',
            render: (entry: NameEntry) => entry.first ?? '---',
            value: (entry: NameEntry) => entry.first,
            sortable: true
        },
        {
            id: 'middle',
            name: 'Middle',
            render: (entry: NameEntry) => entry.middle ?? '---',
            value: (entry: NameEntry) => entry.middle,
            sortable: true
        },
        {
            id: 'suffix',
            name: 'Suffix',
            render: (entry: NameEntry) => entry.suffix ?? '---',
            value: (entry: NameEntry) => entry.suffix,
            sortable: true
        },
        {
            id: 'degree',
            name: 'Degree',
            render: (entry: NameEntry) => entry.degree ?? '---',
            value: (entry: NameEntry) => entry.degree,
            sortable: true
        }
    ];

    return <MergePreviewTableCard<NameEntry> id="name" title="Name" columns={columns} data={initialNames} />;
};
