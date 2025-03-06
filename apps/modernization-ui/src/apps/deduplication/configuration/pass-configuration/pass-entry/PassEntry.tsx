import { Pass } from 'apps/deduplication/api/model/Pass';
import classNames from 'classnames';
import { Icon } from 'design-system/icon';
import styles from './pass-entry.module.scss';

type Props = {
    pass: Pass;
    isSelected: boolean;
    selectPass: () => void;
    editName: () => void;
};
export const PassEntry = ({ pass, selectPass, editName, isSelected = false }: Props) => {
    return (
        <div className={classNames(styles.passEntry, isSelected ? styles.selected : '')}>
            <div className={styles.border} />
            <div className={styles.content}>
                <div className={styles.passNameRow}>
                    <button className={styles.nameLink} onClick={selectPass}>
                        {pass.name}
                    </button>
                    <button className={styles.editButton} onClick={editName}>
                        <Icon name="edit" />
                    </button>
                </div>
                <div className={styles.description}>{pass.description}</div>
                <div className={classNames(styles.status, pass.active ? styles.active : styles.inactive)}>
                    {pass.active ? 'Active' : 'Inactive'}
                </div>
            </div>
        </div>
    );
};
