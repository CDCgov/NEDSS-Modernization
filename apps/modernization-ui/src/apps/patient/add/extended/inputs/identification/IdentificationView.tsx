import { IdentificationEntry } from 'apps/patient/data/entry';
import { ValueView } from 'design-system/data-display/ValueView';
import { asName } from 'options';

type Props = {
    entry: IdentificationEntry;
};
export const IdentificationView = ({ entry }: Props) => {
    return (
        <>
            <ValueView title="Identification as of" value={entry.asOf} required />
            <ValueView title="Type" value={asName(entry.type)} required />
            <ValueView title="Assigning authority" value={asName(entry.issuer)} />
            <ValueView title="ID value" value={entry.id} required />
        </>
    );
};
