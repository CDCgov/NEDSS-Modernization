import { AddressEntry } from 'apps/patient/data';
import { ValueView } from 'design-system/data-display/ValueView';
import { Sizing } from 'design-system/field';

type Props = {
    entry: AddressEntry;
    sizing?: Sizing;
};
export const AddressView = ({ entry, sizing }: Props) => {
    return (
        <>
            <ValueView title="Address as of" value={entry.asOf} sizing={sizing} required />
            <ValueView title="Type" value={entry.type?.name} sizing={sizing} required />
            <ValueView title="Use" value={entry.use?.name} sizing={sizing} required />
            <ValueView title="Street address 1" value={entry.address1} sizing={sizing} />
            <ValueView title="Street address 2" value={entry.address2} sizing={sizing} />
            <ValueView title="City" value={entry.city} sizing={sizing} />
            <ValueView title="State" value={entry.state?.name} sizing={sizing} />
            <ValueView title="Zip" value={entry.zipcode} sizing={sizing} />
            <ValueView title="County" value={entry.county?.name} sizing={sizing} />
            <ValueView title="Census tract" value={entry.censusTract} sizing={sizing} />
            <ValueView title="Country" value={entry.country?.name} sizing={sizing} />
            <ValueView title="Address comments" value={entry.comment} sizing={sizing} />
        </>
    );
};
