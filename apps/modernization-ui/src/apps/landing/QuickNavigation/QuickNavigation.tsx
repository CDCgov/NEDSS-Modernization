import { ReactNode } from 'react';

import styles from './quick-navigation.module.scss';

type Props = {
    children: ReactNode;
};

const QuickNavigation = ({ children }: Props) => <div className={styles.navigation}>{children}</div>;

export { QuickNavigation };
