import styles from './data-table.module.scss';

type Props = {
    colSpan: number;
};

export const NoDataRow = ({ colSpan }: Props) => {
    return (
        <tr>
            <td colSpan={colSpan} className={styles.noData}>
                No data has been added.
            </td>
        </tr>
    );
};
