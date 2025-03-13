import { IdentificationEntry } from 'apps/patient/data';
import { ValueView } from 'design-system/data-display/ValueView';
import { Sizing } from 'design-system/field';

type Props = {
    entry: IdentificationEntry;
    sizing?: Sizing;
};
export const IdentificationView = ({ entry, sizing }: Props) => {
    return (
        <>
            <ValueView title="Identification as of" value={entry.asOf} sizing={sizing} required />
            <ValueView title="Type" value={entry.type?.name} sizing={sizing} required />
            <ValueView title="Assigning authority" value={entry.issuer?.name} sizing={sizing} />
            <ValueView title="ID value" value={entry.id} sizing={sizing} required />
        </>
    );
};
