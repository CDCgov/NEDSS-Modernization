import { AddressEntry } from 'apps/patient/data/entry';
import { ValueView } from 'design-system/data-display/ValueView';
import { asName } from 'options';

type Props = {
    entry: AddressEntry;
};
export const AddressView = ({ entry }: Props) => {
    return (
        <>
            <ValueView title="Address as of" value={entry.asOf} required />
            <ValueView title="Type" value={asName(entry.type)} required />
            <ValueView title="Use" value={asName(entry.use)} required />
            <ValueView title="Street address 1" value={entry.address1} />
            <ValueView title="Street address 2" value={entry.address2} />
            <ValueView title="City" value={entry.city} />
            <ValueView title="State" value={asName(entry.state)} />
            <ValueView title="Zip" value={entry.zipcode} />
            <ValueView title="County" value={asName(entry.county)} />
            <ValueView title="Census tract" value={entry.censusTract} />
            <ValueView title="Country" value={asName(entry.country)} />
            <ValueView title="Address comments" value={entry.comment} />
        </>
    );
};
