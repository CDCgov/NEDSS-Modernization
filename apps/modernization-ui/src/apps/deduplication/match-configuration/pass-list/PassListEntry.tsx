import classNames from 'classnames';
import styles from './pass-list-entry.module.scss';

type Props = {
    name: string;
    description: string;
    isActive: boolean;
    isSelected: boolean;
    onClick: () => void;
};
export const PassListEntry = ({ name, description, isActive, isSelected, onClick }: Props) => {
    return (
        <section onClick={onClick} className={classNames(styles.passListEntry, isSelected ? styles.activeEntry : '')}>
            <div className={styles.border} />
            <div className={styles.info}>
                <div className={styles.name}>{name}</div>
                <div className={styles.description}>{description}</div>
                <div className={classNames(styles.status, isActive ? styles.active : styles.inactive)}>
                    {isActive ? 'Active' : 'Inactive'}
                </div>
            </div>
        </section>
    );
};
