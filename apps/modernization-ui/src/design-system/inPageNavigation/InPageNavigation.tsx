import React, { useEffect } from 'react';
import styles from './InPageNavigation.module.scss';
import classNames from 'classnames';
import useInPageNavigation from './useInPageNavigation';
import { Link, useLocation } from 'react-router';

export interface NavSection {
    id: string;
    label: string;
}

export interface InPageNavigationProps {
    sections: NavSection[];
    title?: string;
}

export const InPageNavigation: React.FC<InPageNavigationProps> = ({ sections, title = 'On this page' }) => {
    useInPageNavigation(0);
    const location = useLocation();

    useEffect(() => {
        if (location.hash !== undefined && location.hash.length > 0) {
            const element = document.getElementById(location.hash.substring(1));
            if (element) {
                element.scrollIntoView({ behavior: 'smooth' });
            }
        }
    }, [location.hash]);

    return (
        <nav>
            <div className={styles.navTitle}>{title}</div>
            <div className={styles.navOptions}>
                {sections.map((section) => (
                    <Link
                        id={`inPageNav-${section.id}`}
                        className={classNames(styles.navOptions)}
                        key={section.id}
                        to={`${window.location.pathname}#${section.id}`}>
                        {section.label}
                    </Link>
                ))}
            </div>
        </nav>
    );
};
