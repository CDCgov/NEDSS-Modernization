import { ReactNode } from 'react';
import { TabNavigation, TabNavigationEntry } from 'components/TabNavigation/TabNavigation';

import styles from './search-navigation.module.scss';

type ActionRenderer = () => ReactNode;

type Props = {
    action?: ActionRenderer;
};

const SearchNavigation = ({ action }: Props) => {
    return (
        <nav className={styles.navigation}>
            <h1>Search for:</h1>
            <TabNavigation className={styles.tabs}>
                <TabNavigationEntry path="/search/patient">Patient</TabNavigationEntry>
                <TabNavigationEntry path="/search/investigation">Investigation</TabNavigationEntry>
                <TabNavigationEntry path="/search/laboratory-search">Laboratory Report</TabNavigationEntry>
            </TabNavigation>
            {action && action()}
        </nav>
    );
};

export { SearchNavigation };
