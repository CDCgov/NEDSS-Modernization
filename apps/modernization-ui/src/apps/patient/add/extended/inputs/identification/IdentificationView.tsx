import { IdentificationEntry } from 'apps/patient/data';
import { ValueView } from 'design-system/data-display/ValueView';

type Props = {
    entry: IdentificationEntry;
};
export const IdentificationView = ({ entry }: Props) => {
    return (
        <>
            <ValueView title="Identification as of" value={entry.asOf} required />
            <ValueView title="Type" value={entry.type?.name} required />
            <ValueView title="Assigning authority" value={entry.issuer?.name} />
            <ValueView title="ID value" value={entry.id} required />
        </>
    );
};
