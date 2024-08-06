import { Column, DataTable } from 'design-system/table';
import { Icon } from '@trussworks/react-uswds';
import styles from './name-fields-extended.module.scss';

interface NameFields {
    id: string;
    asOf: string;
    type: string;
    last: string;
    first: string;
    middle: string;
    suffix: string;
}

export const NameFieldsTable = () => {
    const columns: Column<NameFields>[] = [
        { id: 'asOf', name: 'As of', render: (row) => row.asOf },
        { id: 'type', name: 'Type', render: (row) => row.type },
        { id: 'last', name: 'Last', render: (row) => row.last },
        { id: 'first', name: 'First', render: (row) => row.first },
        { id: 'middle', name: 'Middle', render: (row) => row.middle },
        { id: 'suffix', name: 'Suffix', render: (row) => row.suffix },
        {
            id: '',
            name: '',
            render: (row) => (
                <>
                    <Icon.Visibility
                        style={{ cursor: 'pointer' }}
                        size={3}
                        onClick={() => console.log('Hide', row.id)}
                    />
                    <Icon.Edit style={{ cursor: 'pointer' }} size={3} onClick={() => console.log('Edit', row.id)} />
                    <Icon.Delete style={{ cursor: 'pointer' }} size={3} onClick={() => console.log('Delete', row.id)} />
                </>
            )
        }
    ];

    const data: NameFields[] = [
        { id: '123', asOf: '10/18/2020', type: 'Legal', last: 'Osborne', first: 'Ozzy', middle: 'John', suffix: 'Esq' }
    ];

    return (
        <div className={styles.table}>
            <DataTable id="name-fields-table" columns={columns} data={data} />
        </div>
    );
};
