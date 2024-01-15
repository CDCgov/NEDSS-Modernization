import React, { RefObject } from 'react';

import styles from './page-content.module.scss';
import { Icon, ModalRef, ModalToggleButton } from '@trussworks/react-uswds';
import { Icon as EQIcon } from 'components/Icon/Icon';

type PageSideMenuProps = {
    onAddSection: () => void;
};

export const PageSideMenu = ({ onAddSection }: PageSideMenuProps) => {
    return (
        <div className={styles.sideMenu}>
            <ul className={styles.list}>
                <li>
                    <Icon.Add
                        size={4}
                        onClick={() => {
                            onAddSection();
                        }}
                    />
                    Add section
                </li>
                <li>
                    <EQIcon name="reorder" />
                    Reorder
                </li>
            </ul>
        </div>
    );
};
