import { NameEntry } from 'apps/patient/data/name';
import { ValueView } from 'design-system/data-display/ValueView';
import { Sizing } from 'design-system/field';

type Props = {
    entry: NameEntry;
    sizing?: Sizing;
};

export const NameEntryView = ({ entry, sizing }: Props) => {
    return (
        <>
            <ValueView title="As of" value={entry.asOf} sizing={sizing} required />
            <ValueView title="Type" value={entry.type?.name} sizing={sizing} required />
            <ValueView title="Prefix" value={entry.prefix?.name} sizing={sizing} />
            <ValueView title="Last" value={entry.last} sizing={sizing} />
            <ValueView title="Second last" value={entry.secondLast} sizing={sizing} />
            <ValueView title="First" value={entry.first} sizing={sizing} />
            <ValueView title="Middle" value={entry.middle} sizing={sizing} />
            <ValueView title="Second middle" value={entry.secondMiddle} sizing={sizing} />
            <ValueView title="Suffix" value={entry.suffix?.name} sizing={sizing} />
            <ValueView title="Degree" value={entry.degree?.name} sizing={sizing} />
        </>
    );
};
