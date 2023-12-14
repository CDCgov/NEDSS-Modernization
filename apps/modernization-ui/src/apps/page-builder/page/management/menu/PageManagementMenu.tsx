import { ReactNode } from 'react';

import styles from './page-management-menu.module.scss';

type PageManagementMenuProps = {
    children: ReactNode;
};

const PageManagementMenu = ({ children }: PageManagementMenuProps) => <menu className={styles.menu}>{children}</menu>;

export { PageManagementMenu };
