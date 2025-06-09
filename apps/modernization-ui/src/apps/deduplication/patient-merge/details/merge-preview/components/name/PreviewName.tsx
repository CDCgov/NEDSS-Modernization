import React, { useEffect, useState } from 'react';
import { ReadOnlyRepeatingBlock } from '../ReadOnlyRepeatingBlock/ReadOnlyRepeatingBlock';
import { MergeCandidate, MergeName } from '../../../../../api/model/MergeCandidate';
import { NameId } from '../../../merge-review/model/PatientMergeForm';
import { toMMDDYYYY } from '../../utils/dateUtils';

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

    console.log('Selected Names:', selectedNames);

    const initialNames: NameEntry[] = detailedNames.map((n) => ({
        personUid: n.personUid,
        sequence: n.sequence,
        asOf: toMMDDYYYY(n.asOf),
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

    const columns = [
        { id: 'asOf', name: 'As of', render: (entry: NameEntry) => entry.asOf || '---' },
        { id: 'type', name: 'Type', render: (entry: NameEntry) => entry.type || '---' },
        { id: 'prefix', name: 'Prefix', render: (entry: NameEntry) => entry.prefix || '---' },
        { id: 'last', name: 'Last', render: (entry: NameEntry) => entry.last || '---' },
        { id: 'first', name: 'First', render: (entry: NameEntry) => entry.first || '---' },
        { id: 'middle', name: 'Middle', render: (entry: NameEntry) => entry.middle || '---' },
        { id: 'suffix', name: 'Suffix', render: (entry: NameEntry) => entry.suffix || '---' },
        { id: 'degree', name: 'Degree', render: (entry: NameEntry) => entry.degree || '---' }
    ];

    return (
        <>
            <ReadOnlyRepeatingBlock<NameEntry> id="names" title="Names" columns={columns} values={names} />
            {dirty && <p></p>}
        </>
    );
};
