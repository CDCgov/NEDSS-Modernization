import { MergeIdentification, MergeCandidate } from '../../../../../api/model/MergeCandidate';
import { IdentificationId } from '../../../merge-review/model/PatientMergeForm';
import { format, parseISO } from 'date-fns';
import { MergePreviewTableCard } from '../shared/preview-card-table/MergePreviewTableCard';
import { Column } from 'design-system/table';

type IdentificationEntry = {
    asOf: string;
    type: string;
    assigningAuthority?: string;
    value: string;
};

type PreviewIdentificationProps = {
    selectedIdentifications: IdentificationId[];
    mergeCandidates: MergeCandidate[];
};

export const PreviewIdentification = ({ selectedIdentifications, mergeCandidates }: PreviewIdentificationProps) => {
    const detailedIdentifications: MergeIdentification[] = mergeCandidates
        .flatMap((m) => m.identifications)
        .filter((id) =>
            selectedIdentifications.some((sid) => sid.personUid === id.personUid && sid.sequence === id.sequence)
        )
        .sort((a, b) => {
            return parseISO(a.asOf) > parseISO(b.asOf) ? -1 : 1;
        });

    const identifications: IdentificationEntry[] = detailedIdentifications.map((id) => ({
        ...id,
        asOf: format(parseISO(id.asOf), 'MM/dd/yyyy')
    }));

    const columns: Column<IdentificationEntry>[] = [
        {
            id: 'asOf',
            name: 'As of',
            render: (entry: IdentificationEntry) => entry.asOf ?? '---',
            value: (entry: IdentificationEntry) => entry.asOf,
            sortable: true
        },
        {
            id: 'type',
            name: 'Type',
            render: (entry: IdentificationEntry) => entry.type ?? '---',
            value: (entry: IdentificationEntry) => entry.type,
            sortable: true
        },
        {
            id: 'assigningAuthority',
            name: 'Assigning Authority',
            render: (entry: IdentificationEntry) => entry.assigningAuthority ?? '---',
            value: (entry: IdentificationEntry) => entry.assigningAuthority,
            sortable: true
        },
        {
            id: 'value',
            name: 'Value',
            render: (entry: IdentificationEntry) => entry.value ?? '---',
            value: (entry: IdentificationEntry) => entry.value,
            sortable: true
        }
    ];

    return (
        <MergePreviewTableCard<IdentificationEntry>
            id="identification"
            title="Identification"
            columns={columns}
            data={identifications}
        />
    );
};
