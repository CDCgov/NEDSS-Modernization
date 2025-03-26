import { Pass } from 'apps/deduplication/api/model/Pass';
import classNames from 'classnames';
import { Icon } from 'design-system/icon';
import styles from './pass-entry.module.scss';
import { Shown } from 'conditional-render';

type Props = {
    pass: Pass;
    isSelected: boolean;
    onSelectPass: (pass: Pass) => void;
    onEditName: (pass: Pass) => void;
};
export const PassEntry = ({ pass, onSelectPass, onEditName, isSelected = false }: Props) => {
    return (
        <div className={classNames(styles.passEntry, isSelected ? styles.selected : '')}>
            <div className={styles.border} />
            <div className={styles.content}>
                <div className={styles.passNameRow}>
                    <button className={styles.nameLink} onClick={() => onSelectPass(pass)}>
                        {pass.name}
                    </button>
                    <Shown when={pass.id !== undefined}>
                        <button className={styles.editButton} onClick={() => onEditName(pass)}>
                            <Icon name="edit" aria-label={`Edit ${pass.name}`} />
                        </button>
                    </Shown>
                </div>
                <div className={styles.description}>{pass.description}</div>
                <div className={classNames(styles.status, pass.active ? styles.active : styles.inactive)}>
                    {pass.active ? 'Active' : 'Inactive'}
                </div>
            </div>
        </div>
    );
};
