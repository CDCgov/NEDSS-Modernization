import classNames from 'classnames';
import styles from './pass-list-entry.module.scss';
import { Pass } from '../model/Pass';

type Props = {
    pass: Pass;
    activePass: boolean;
    onClick: () => void;
};
export const PassListEntry = ({ pass, activePass, onClick }: Props) => {
    return (
        <section onClick={onClick} className={classNames(styles.passListEntry, activePass ? styles.activeEntry : '')}>
            <div className={styles.border} />
            <div className={styles.info}>
                <div className={styles.name}>{pass.name}</div>
                <div className={styles.description}>{pass.description}</div>
                <div className={classNames(styles.status, pass.active ? styles.active : styles.inactive)}>
                    {pass.active ? 'Active' : 'Inactive'}
                </div>
            </div>
        </section>
    );
};
