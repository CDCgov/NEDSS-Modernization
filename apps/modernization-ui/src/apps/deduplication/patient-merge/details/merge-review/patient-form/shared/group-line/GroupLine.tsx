import styles from './group-line.module.scss';

type Props = {
    groupType?: 'blank' | 'linked' | 'last';
};
// Creates a 2x2 grid of divs that can "connect" data points
export const GroupLine = ({ groupType = 'blank' }: Props) => {
    return (
        <div className={styles.groupLine}>
            <div className={styles.row}>
                <div />
                <div className={groupType !== 'blank' ? styles.top : ''} />
            </div>
            <div className={styles.row}>
                <div />
                <div className={groupType === 'linked' ? styles.linked : ''} />
            </div>
        </div>
    );
};
