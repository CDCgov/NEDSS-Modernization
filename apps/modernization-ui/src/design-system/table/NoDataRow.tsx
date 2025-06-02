import { ReactNode } from 'react';
import styles from './data-table.module.scss';

type NoDataRowProps = {
    columns: number;
    children: ReactNode;
};

const NoDataRow = ({ columns, children }: NoDataRowProps) => (
    <tr>
        <td colSpan={columns} className={styles.noData}>
            {children}
        </td>
    </tr>
);

export { NoDataRow };
