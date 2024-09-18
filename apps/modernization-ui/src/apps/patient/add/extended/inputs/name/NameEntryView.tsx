import { NameEntry } from 'apps/patient/data/entry';
import { DataDisplay } from 'design-system/data-display/DataDisplay';

type Props = {
    entry: NameEntry;
};

export const NameEntryView = ({ entry }: Props) => {
    return (
        <>
            <DataDisplay title="As of" value={entry.asOf} required />
            <DataDisplay title="Type" value={entry.type.name} required />
            <DataDisplay title="Prefix" value={entry.prefix?.name} />
            <DataDisplay title="Last" value={entry.last} />
            <DataDisplay title="Second last" value={entry.secondLast} />
            <DataDisplay title="First" value={entry.first} />
            <DataDisplay title="Middle" value={entry.middle} />
            <DataDisplay title="Second middle" value={entry.secondMiddle} />
            <DataDisplay title="Suffix" value={entry.suffix?.name} />
            <DataDisplay title="Degree" value={entry.degree?.name} />
        </>
    );
};
