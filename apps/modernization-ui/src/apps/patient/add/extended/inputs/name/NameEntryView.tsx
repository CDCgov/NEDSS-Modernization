import { NameEntry } from 'apps/patient/data/name';
import { ValueView } from 'design-system/data-display/ValueView';

type Props = {
    entry: NameEntry;
};

export const NameEntryView = ({ entry }: Props) => {
    return (
        <>
            <ValueView title="As of" value={entry.asOf} required />
            <ValueView title="Type" value={entry.type?.name} required />
            <ValueView title="Prefix" value={entry.prefix?.name} />
            <ValueView title="Last" value={entry.last} />
            <ValueView title="Second last" value={entry.secondLast} />
            <ValueView title="First" value={entry.first} />
            <ValueView title="Middle" value={entry.middle} />
            <ValueView title="Second middle" value={entry.secondMiddle} />
            <ValueView title="Suffix" value={entry.suffix?.name} />
            <ValueView title="Degree" value={entry.degree?.name} />
        </>
    );
};
