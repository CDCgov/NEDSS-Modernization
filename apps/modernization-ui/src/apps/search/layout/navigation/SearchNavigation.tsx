import { ReactNode } from 'react';
import { TabNavigation, TabNavigationEntry } from 'components/TabNavigation/TabNavigation';

import styles from './search-navigation.module.scss';

type ActionsRenderer = () => ReactNode;

type Props = {
    actions?: ActionsRenderer;
};

const SearchNavigation = ({ actions }: Props) => {
    return (
        <nav className={styles.navigation}>
            <h1>Search for:</h1>
            <TabNavigation className={styles.tabs}>
                <TabNavigationEntry path="/search/patients">Patients</TabNavigationEntry>
                <TabNavigationEntry path="/search/investigations">Investigations</TabNavigationEntry>
                <TabNavigationEntry path="/search/lab-reports">Lab Reports</TabNavigationEntry>
            </TabNavigation>
            {actions && actions()}
        </nav>
    );
};

export { SearchNavigation };
