import React, { useEffect, useState } from 'react';
import { MergeCandidate, MergeName } from '../../../../../api/model/MergeCandidate';
import { NameId } from '../../../merge-review/model/PatientMergeForm';
import { format, parseISO } from 'date-fns';
import { SortableTableCard } from '../Card/SortableTableCard';
import { Column } from 'design-system/table/DataTable';

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
    const detailedNames: MergeName[] = selectedNames.flatMap(({ personUid, sequence }) => {
        const candidate = mergeCandidates.find((mc) => mc.personUid === personUid);
        if (!candidate) {
            console.warn(`No mergeCandidate found for UID ${personUid}`);
            return [];
        }
        const matches = candidate.names.filter((name) => String(name.sequence) === String(sequence));
        if (matches.length === 0) {
            console.warn(`No names matched for UID ${personUid} and sequence ${sequence}`);
        }
        return matches;
    });

    const initialNames: NameEntry[] = detailedNames.map((n) => ({
        personUid: n.personUid,
        sequence: n.sequence,
        asOf: format(parseISO(n.asOf), 'MM/dd/yyyy'),
        type: n.type,
        prefix: n.prefix,
        last: n.last,
        first: n.first,
        middle: n.middle,
        suffix: n.suffix,
        degree: n.degree
    }));

    const [names] = useState(initialNames);
    const [dirty, setDirty] = useState(false);

    const deepEqual = (a: NameEntry[], b: NameEntry[]) => {
        if (a.length !== b.length) return false;
        return a.every((itemA, index) => {
            const itemB = b[index];
            return Object.keys(itemA).every((key) => (itemA as any)[key] === (itemB as any)[key]);
        });
    };

    useEffect(() => {
        setDirty(!deepEqual(names, initialNames));
    }, [names, initialNames]);

    const columns: Column<NameEntry>[] = [
        {
            id: 'asOf',
            name: 'As of',
            render: (entry: NameEntry) => entry.asOf || '---',
            value: (entry: NameEntry) => entry.asOf,
            sortable: true
        },
        {
            id: 'type',
            name: 'Type',
            render: (entry: NameEntry) => entry.type || '---',
            value: (entry: NameEntry) => entry.type,
            sortable: true
        },
        {
            id: 'prefix',
            name: 'Prefix',
            render: (entry: NameEntry) => entry.prefix || '---',
            value: (entry: NameEntry) => entry.prefix,
            sortable: true
        },
        {
            id: 'last',
            name: 'Last',
            render: (entry: NameEntry) => entry.last || '---',
            value: (entry: NameEntry) => entry.last,
            sortable: true
        },
        {
            id: 'first',
            name: 'First',
            render: (entry: NameEntry) => entry.first || '---',
            value: (entry: NameEntry) => entry.first,
            sortable: true
        },
        {
            id: 'middle',
            name: 'Middle',
            render: (entry: NameEntry) => entry.middle || '---',
            value: (entry: NameEntry) => entry.middle,
            sortable: true
        },
        {
            id: 'suffix',
            name: 'Suffix',
            render: (entry: NameEntry) => entry.suffix || '---',
            value: (entry: NameEntry) => entry.suffix,
            sortable: true
        },
        {
            id: 'degree',
            name: 'Degree',
            render: (entry: NameEntry) => entry.degree || '---',
            value: (entry: NameEntry) => entry.degree,
            sortable: true
        }
    ];

    return (
        <>
            <SortableTableCard<NameEntry> id="name" title="Name" columns={columns} data={names} />
            {dirty && <p></p>}
        </>
    );
};
