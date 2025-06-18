import { ReactNode } from 'react';

import styles from './no-data-row.module.scss';

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
