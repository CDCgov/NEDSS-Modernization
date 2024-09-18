import { AddressEntry } from 'apps/patient/data/entry';
import { DataDisplay } from 'design-system/data-display/DataDisplay';

type Props = {
    entry: AddressEntry;
};
export const AddressView = ({ entry }: Props) => {
    return (
        <>
            <DataDisplay title="Address as of" value={entry.asOf} required />
            <DataDisplay title="Type" value={entry.type.name} required />
            <DataDisplay title="Use" value={entry.use.name} required />
            <DataDisplay title="Street address 1" value={entry.address1} />
            <DataDisplay title="Street address 2" value={entry.address2} />
            <DataDisplay title="City" value={entry.city} />
            <DataDisplay title="State" value={entry.state?.name} />
            <DataDisplay title="Zip" value={entry.zipcode} />
            <DataDisplay title="County" value={entry.county?.name} />
            <DataDisplay title="Census tract" value={entry.censusTract} />
            <DataDisplay title="Country" value={entry.country?.name} />
            <DataDisplay title="Address comments" value={entry.comment} />
        </>
    );
};
