import { NameEntry } from 'apps/patient/data/entry';
import { ValueView } from 'design-system/data-display/ValueView';
import { asName } from 'options';

type Props = {
    entry: NameEntry;
};

export const NameEntryView = ({ entry }: Props) => {
    return (
        <>
            <ValueView title="As of" value={entry.asOf} required />
            <ValueView title="Type" value={asName(entry.type)} required />
            <ValueView title="Prefix" value={asName(entry.prefix)} />
            <ValueView title="Last" value={entry.last} />
            <ValueView title="Second last" value={entry.secondLast} />
            <ValueView title="First" value={entry.first} />
            <ValueView title="Middle" value={entry.middle} />
            <ValueView title="Second middle" value={entry.secondMiddle} />
            <ValueView title="Suffix" value={asName(entry.suffix)} />
            <ValueView title="Degree" value={asName(entry.degree)} />
        </>
    );
};
