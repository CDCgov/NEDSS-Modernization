import { DataTable } from 'design-system/table';
import styles from './name-fields-extended.module.scss';

export const NameFieldsTable = () => {
    const columns = [
        { id: 'asOf', name: 'As of', render: () => <p>As of</p> },
        { id: 'type', name: 'Type', render: () => <p>Type</p> },
        { id: 'last', name: 'Last', render: () => <p>Last</p> },
        { id: 'first', name: 'First', render: () => <p>First</p> },
        { id: 'middle', name: 'Middle', render: () => <p>Middle</p> },
        { id: 'suffix', name: 'Suffix', render: () => <p>Suffix</p> }
    ];

    const data = [{ value: 'asdf' }, { value: 'asdf' }, { value: 'asdf' }, { value: 'asdf' }, { value: 'asdf' }];

    return (
        <div className={styles.table}>
            <DataTable id="name-fields-table" columns={columns} data={data} />
        </div>
    );
};
