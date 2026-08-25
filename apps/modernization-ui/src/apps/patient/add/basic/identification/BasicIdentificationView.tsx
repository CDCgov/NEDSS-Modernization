import { ValueView } from 'design-system/data-display/ValueView';
import { Sizing } from 'design-system/field';

import { BasicIdentificationEntry } from '../entry';

type Props = {
    entry: BasicIdentificationEntry;
    sizing?: Sizing;
};
export const BasicIdentificationView = ({ entry, sizing }: Props) => {
    return (
        <>
            <ValueView title="Type" value={entry.type?.name} sizing={sizing} required={true} />
            <ValueView title="Assigning authority" value={entry.issuer?.name} sizing={sizing} />
            <ValueView title="ID value" value={entry.id} sizing={sizing} required={true} />
        </>
    );
};
