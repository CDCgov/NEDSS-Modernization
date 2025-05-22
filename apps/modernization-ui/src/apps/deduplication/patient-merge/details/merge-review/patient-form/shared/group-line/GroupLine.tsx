import styles from './group-line.module.scss';

type Props = {
    last?: boolean;
};
// Creates a 2x2 grid of divs that can "connect" data points
export const GroupLine = ({ last = false }: Props) => {
    return (
        <div className={styles.groupLine}>
            <div className={styles.row}>
                <div />
                <div className={styles.top} />
            </div>
            <div className={styles.row}>
                <div />
                <div className={last ? '' : styles.bottom} />
            </div>
        </div>
    );
};
