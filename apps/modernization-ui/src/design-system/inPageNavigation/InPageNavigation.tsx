import { FC, useEffect } from 'react';

import classNames from 'classnames';
import { Link, useLocation } from 'react-router';
import { logErrorToUserConsole } from 'utils/logging';

import styles from './InPageNavigation.module.scss';
import useInPageNavigation from './useInPageNavigation';

export interface NavSection {
    id: string;
    label: string;
}

export interface InPageNavigationProps {
    sections: NavSection[];
    title?: string;
}

export const InPageNavigation: FC<InPageNavigationProps> = ({ sections, title = 'On this page' }) => {
    useInPageNavigation(0);
    const location = useLocation();

    useEffect(() => {
        if (location.hash !== undefined && location.hash.length > 0) {
            const element = document.getElementById(location.hash.substring(1));
            if (element) {
                element.scrollIntoView({ behavior: 'smooth' });
            } else {
                logErrorToUserConsole(`InPageNavigation failed to navigate to ${location.hash}`);
            }
        }
    }, [location.hash]);

    return (
        <nav aria-label={title}>
            <div className={styles.navTitle}>{title}</div>
            <div className={styles.navOptions}>
                {sections.map((section) => (
                    <Link
                        key={section.id}
                        id={`inPageNav-${section.id}`}
                        to={{ pathname: location.pathname, hash: section.id }}
                        className={classNames(styles.navOption)}
                        state={location.state}
                    >
                        {section.label}
                    </Link>
                ))}
            </div>
        </nav>
    );
};
