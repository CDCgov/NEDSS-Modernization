import { Sizing } from 'design-system/field';
import { BasicIdentificationEntry } from '../entry';
import { ValueView } from 'design-system/data-display/ValueView';

type Props = {
    entry: BasicIdentificationEntry;
    sizing?: Sizing;
};
export const BasicIdentificationView = ({ entry, sizing }: Props) => {
    return (
        <>
            <ValueView title="Type" value={entry.type?.name} sizing={sizing} required />
            <ValueView title="Assigning authority" value={entry.issuer?.name} sizing={sizing} />
            <ValueView title="ID value" value={entry.id} sizing={sizing} required />
        </>
    );
};
