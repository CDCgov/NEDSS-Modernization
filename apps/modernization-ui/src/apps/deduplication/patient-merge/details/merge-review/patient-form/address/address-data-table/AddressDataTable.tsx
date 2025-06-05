import { displayAddressText } from 'address/display';
import { MergeAddress, MergeCandidate } from 'apps/deduplication/api/model/MergeCandidate';
import { Column } from 'design-system/table';
import { useFieldArray, useFormContext } from 'react-hook-form';
import { PatientMergeForm } from '../../../model/PatientMergeForm';
import { MergeDataTable } from '../../shared/merge-data-table/MergeDataTable';
import { toDateDisplay } from '../../shared/toDateDisplay';

type Props = {
    patientData: MergeCandidate;
    selectedAddress?: MergeAddress;
    onViewAddress: (address: MergeAddress) => void;
};
export const AddressDataTable = ({ patientData, selectedAddress, onViewAddress }: Props) => {
    const form = useFormContext<PatientMergeForm>();
    const { fields, append, remove } = useFieldArray({
        control: form.control,
        name: 'addresses'
    });

    const handleAddressSelection = (address: MergeAddress) => {
        const index = fields.findIndex((f) => f.locatorId === address.id);
        if (index > -1) {
            remove(index);
        } else {
            append({ locatorId: address.id });
        }
    };

    const columns: Column<MergeAddress>[] = [
        {
            id: 'as-of',
            name: 'As of',
            render: (n) => toDateDisplay(n.asOf)
        },
        {
            id: 'type',
            name: 'Type',
            render: (n) => `${n.type}/${n.use}`
        },
        {
            id: 'address',
            name: 'Address',
            render: (n) => displayAddressText(n)
        }
    ];
    return (
        <MergeDataTable<MergeAddress>
            id={`address-data${patientData.personUid}`}
            columns={columns}
            data={patientData.addresses}
            rowId={(a) => a.id}
            isSelected={(a) => fields.some((f) => f.locatorId === a.id)}
            onSelect={(a) => handleAddressSelection(a)}
            isViewed={(a) => selectedAddress === a}
            onView={(a) => onViewAddress(a)}
        />
    );
};
