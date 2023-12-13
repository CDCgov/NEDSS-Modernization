import React from 'react';

import styles from './page-content.module.scss';
import { Icon } from '@trussworks/react-uswds';
import { Icon as EQIcon } from 'components/Icon/Icon';

export const PageSideMenu = () => {
    return (
        <div className={styles.sideMenu}>
            <ul className={styles.list}>
                <li>
                    <Icon.Add />
                    Add section
                </li>
                <li>
                    <EQIcon name="reorder" />
                    Add section
                </li>
            </ul>
        </div>
    );
};
