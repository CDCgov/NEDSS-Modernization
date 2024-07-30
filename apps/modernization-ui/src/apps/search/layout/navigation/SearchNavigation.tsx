import { ReactNode } from 'react';
import classNames from 'classnames';
import { TabNavigation, TabNavigationEntry } from 'components/TabNavigation/TabNavigation';

import styles from './search-navigation.module.scss';

type ActionsRenderer = () => ReactNode;

type Props = {
    className?: string;
    actions?: ActionsRenderer;
};

const SearchNavigation = ({ className, actions }: Props) => {
    return (
        <nav className={classNames(styles.navigation, className)}>
            <h1>Search for:</h1>
            <TabNavigation className={styles.tabs}>
                <TabNavigationEntry path="/search/patients">Patients</TabNavigationEntry>
                <TabNavigationEntry path="/search/investigations">Investigations</TabNavigationEntry>
                <TabNavigationEntry path="/search/lab-reports">Lab reports</TabNavigationEntry>
            </TabNavigation>
            {actions && actions()}
        </nav>
    );
};

export { SearchNavigation };
