import { displayAddressText } from 'address/display';
import { PatientAddress, PatientData } from 'apps/deduplication/api/model/PatientData';
import { format, parseISO } from 'date-fns';
import { Column } from 'design-system/table';
import { useFieldArray, useFormContext } from 'react-hook-form';
import { PatientMergeForm } from '../../../model/PatientMergeForm';
import { MergeDataTable } from '../../shared/merge-data-table/MergeDataTable';

type Props = {
    patientData: PatientData;
    selectedAddress?: PatientAddress;
    onViewAddress: (address: PatientAddress) => void;
};
export const AddressDataTable = ({ patientData, selectedAddress, onViewAddress }: Props) => {
    const form = useFormContext<PatientMergeForm>();
    const { fields, append, remove } = useFieldArray({
        control: form.control,
        name: 'addresses'
    });

    const handleAddressSelection = (address: PatientAddress) => {
        const index = fields.findIndex((f) => f.locatorId === address.id);
        if (index > -1) {
            remove(index);
        } else {
            append({ locatorId: address.id });
        }
    };

    const columns: Column<PatientAddress>[] = [
        {
            id: 'as-of',
            name: 'As of',
            render: (n) => format(parseISO(n.asOf), 'MM/dd/yyyy')
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
        <MergeDataTable<PatientAddress>
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
