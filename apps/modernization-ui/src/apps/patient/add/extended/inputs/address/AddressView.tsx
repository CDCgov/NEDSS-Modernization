import { AddressFields } from 'apps/patient/profile/addresses/AddressEntry';
import { usePatientAddressCodedValues } from 'apps/patient/profile/addresses/usePatientAddressCodedValues';
import { DataDisplay } from 'design-system/data-display/DataDisplay';
import { useLocationCodedValues } from 'location';

type Props = {
    entry: AddressFields;
};
export const AddressView = ({ entry }: Props) => {
    const coded = usePatientAddressCodedValues();
    const location = useLocationCodedValues();
    const counties = location.counties.byState(entry.state ?? '');

    return (
        <>
            <DataDisplay title="Address as of" value={entry.asOf} required />
            <DataDisplay title="Type" value={coded.types.find((e) => e.value === entry.type)?.name} required />
            <DataDisplay title="Use" value={coded.uses.find((e) => e.value === entry.use)?.name} required />
            <DataDisplay title="Street address 1" value={entry.address1} />
            <DataDisplay title="Street address 2" value={entry.address2} />
            <DataDisplay title="City" value={entry.city} />
            <DataDisplay title="State" value={location.states.all.find((s) => s.value === entry.state)?.name} />
            <DataDisplay title="Zip" value={entry.zipcode} />
            <DataDisplay title="County" value={counties.find((c) => c.value === entry.county)?.name} />
            <DataDisplay title="Census tract" value={entry.censusTract} />
            <DataDisplay title="Country" value={location.countries.find((c) => c.value === entry.country)?.name} />
            <DataDisplay title="Address comments" value={entry.comment} />
        </>
    );
};
