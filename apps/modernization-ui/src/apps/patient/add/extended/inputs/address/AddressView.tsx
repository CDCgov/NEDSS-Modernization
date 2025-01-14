import { AddressEntry } from 'apps/patient/data';
import { ValueView } from 'design-system/data-display/ValueView';

type Props = {
    entry: AddressEntry;
};
export const AddressView = ({ entry }: Props) => {
    return (
        <>
            <ValueView title="Address as of" value={entry.asOf} required />
            <ValueView title="Type" value={entry.type?.name} required />
            <ValueView title="Use" value={entry.use?.name} required />
            <ValueView title="Street address 1" value={entry.address1} />
            <ValueView title="Street address 2" value={entry.address2} />
            <ValueView title="City" value={entry.city} />
            <ValueView title="State" value={entry.state?.name} />
            <ValueView title="Zip" value={entry.zipcode} />
            <ValueView title="County" value={entry.county?.name} />
            <ValueView title="Census tract" value={entry.censusTract} />
            <ValueView title="Country" value={entry.country?.name} />
            <ValueView title="Address comments" value={entry.comment} />
        </>
    );
};
