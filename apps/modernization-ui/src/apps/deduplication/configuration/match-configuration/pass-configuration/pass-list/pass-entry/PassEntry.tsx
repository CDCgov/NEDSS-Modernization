import { Pass } from 'apps/deduplication/api/model/Pass';
import classNames from 'classnames';
import { Icon } from 'design-system/icon';
import styles from './pass-entry.module.scss';
import { Shown } from 'conditional-render';
import React from 'react';

type Props = {
    pass: Pass;
    isSelected: boolean;
    onSelectPass: (pass: Pass) => void;
    onEditName: (pass: Pass) => void;
};

export const PassEntry = ({ pass, onSelectPass, onEditName, isSelected = false }: Props) => {
    const handleEditClick = (e: React.MouseEvent) => {
        e.stopPropagation();
        onEditName(pass);
    };

    return (
        <button
            type="button"
            className={classNames(styles.passEntry, isSelected && styles.selected)}
            aria-label={`Select ${pass.name}`}
            onClick={() => onSelectPass(pass)}>
            <div className={styles.border} />
            <div className={styles.content}>
                <div className={styles.passNameRow}>
                    <span className={styles.nameLink}>{pass.name}</span>
                    <Shown when={pass.id !== undefined}>
                        <button
                            type="button"
                            className={styles.editButton}
                            onClick={handleEditClick}
                            aria-label={`Edit name`}
                            data-tooltip-position="top"
                            data-tooltip-offset="left">
                            <Icon name="edit" />
                        </button>
                    </Shown>
                </div>
                <div className={styles.description}>{pass.description}</div>
                <div className={classNames(styles.status, pass.active ? styles.active : styles.inactive)}>
                    {pass.active ? 'Active' : 'Inactive'}
                </div>
            </div>
        </button>
    );
};
