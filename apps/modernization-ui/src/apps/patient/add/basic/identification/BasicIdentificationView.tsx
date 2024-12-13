import { BasicIdentificationEntry } from '../entry';
import { ValueView } from 'design-system/data-display/ValueView';

type Props = {
    entry: BasicIdentificationEntry;
};
export const BasicIdentificationView = ({ entry }: Props) => {
    return (
        <>
            <ValueView title="Type" value={entry.type?.name} required />
            <ValueView title="Assigning authority" value={entry.issuer?.name} />
            <ValueView title="ID value" value={entry.id} required />
        </>
    );
};
