import { IdentificationEntry } from 'apps/patient/data/entry';
import { DataDisplay } from 'design-system/data-display/DataDisplay';

type Props = {
    entry: IdentificationEntry;
};
export const IdentificationView = ({ entry }: Props) => {
    return (
        <>
            <DataDisplay title="Identification as of" value={entry.asOf} required />
            <DataDisplay title="Type" value={entry.type.name} required />
            <DataDisplay title="Assigning authority" value={entry.issuer?.name} />
            <DataDisplay title="ID value" value={entry.id} required />
        </>
    );
};
