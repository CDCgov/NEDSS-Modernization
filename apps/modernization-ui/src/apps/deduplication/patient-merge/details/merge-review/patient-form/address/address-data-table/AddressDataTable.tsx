import { displayAddressText } from 'address/display';
import { PatientAddress, PatientData } from 'apps/deduplication/api/model/PatientData';
import { format, parseISO } from 'date-fns';
import { Button } from 'design-system/button';
import { Checkbox } from 'design-system/checkbox';
import { Icon } from 'design-system/icon';
import { Column, DataTable } from 'design-system/table';
import { useFieldArray, useFormContext } from 'react-hook-form';
import { PatientMergeForm } from '../../../model/PatientMergeForm';
import styles from './address-data-table.module.scss';

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
            id: 'selection',
            name: '',
            render: (a) => (
                <Checkbox
                    id={`address-select:${a.id}`}
                    label=""
                    className={styles.checkBox}
                    onChange={() => handleAddressSelection(a)}
                    selected={fields.some((f) => f.locatorId === a.id)}
                />
            )
        },
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
        },
        {
            id: 'view-icon',
            name: '',
            render: (n) => (
                <Button
                    unstyled
                    sizing="small"
                    icon={<Icon name="visibility" className={selectedAddress === n ? styles.selected : ''} />}
                    onClick={() => onViewAddress(n)}
                />
            )
        }
    ];
    return (
        <DataTable<PatientAddress>
            id={`address-data${patientData.personUid}`}
            className={styles.dataTable}
            sizing="small"
            columns={columns}
            data={patientData.addresses ?? []}
        />
    );
};
